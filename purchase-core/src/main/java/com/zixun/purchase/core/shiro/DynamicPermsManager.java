package com.zixun.purchase.core.shiro;

import com.auth0.jwt.JWT;
import com.zixun.purchase.core.spring.SpringUtil;
import com.zixun.purchase.model.PermissionInfo;
import com.zixun.purchase.persist.daorepository.PermissionRepository;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 动态权限控制
 * @author: tiger
 * @date: 18-1-17 下午4:46
 * @version: V1.0
 * @modified by:
 */
@Component
public class DynamicPermsManager {
    private Logger logger = LoggerFactory.getLogger(DynamicPermsManager.class);

    @Autowired
    private PermissionRepository permissionRepository;

    public Map<String, String> LoadPermsFilterChainDefinitions() {
        //配置访问权限
        Map<String, String> urlPermMap = new LinkedHashMap<>(30);

        urlPermMap.put("/api/login", "anon");
        urlPermMap.put("/api/401", "anon");
        urlPermMap.put("/api/users/logininfo", "jwt");

        List<PermissionInfo> permissionInfoList = permissionRepository.selectShiroAllPermission();

        for (PermissionInfo permissionInfo : permissionInfoList) {
            urlPermMap.put(permissionInfo.getUrl(), String.format("jwt,perms[%s]", permissionInfo.getCode()));
        }

        return urlPermMap;
    }

    /**
     * 重新加载权限
     */
    public Boolean updatePermission() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = SpringUtil.getBean(ShiroFilterFactoryBean.class);

        synchronized (shiroFilterFactoryBean) {
            AbstractShiroFilter shiroFilter;

            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean
                        .getObject();
            } catch (Exception e) {
                throw new RuntimeException(
                        "get ShiroFilter from shiroFilterFactoryBean error!");
            }
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
                    .getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver
                    .getFilterChainManager();
            // 清空老的权限控制
            manager.getFilterChains().clear();
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            shiroFilterFactoryBean.setFilterChainDefinitionMap(LoadPermsFilterChainDefinitions());

            // 重新构建生成
            Map<String, String> chains = shiroFilterFactoryBean
                    .getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim()
                        .replace(" ", "");
                manager.createChain(url, chainDefinition);
            }

            logger.info("动态更新权限成功！");
        }

        return true;
    }
}
