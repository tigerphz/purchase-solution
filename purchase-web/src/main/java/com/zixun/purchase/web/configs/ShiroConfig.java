package com.zixun.purchase.web.configs;

import com.zixun.purchase.core.shiro.AuthRealm;
import com.zixun.purchase.core.shiro.StaticConfig;
import com.zixun.purchase.core.shiro.filters.TokenFilter;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-10 下午3:13
 * @version: V1.0
 * @modified by:
 */
@Configuration
public class ShiroConfig {
    private static Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();

        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<>(2);
        filterMap.put("jwt", new TokenFilter());
        bean.setFilters(filterMap);

        // 必须设置 SecurityManager
        bean.setSecurityManager(manager);

        bean.setUnauthorizedUrl("/api/401");

        return bean;
    }

    /**
     * 配置核心安全事务管理器
     *
     * @param authRealm
     * @return
     */
    @Bean(name = "securityManager")
    public SecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm) {
        logger.info("shiro已经加载");

        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(authRealm);

        //使用内存做为shiro的缓存
        manager.setCacheManager(new MemoryConstrainedCacheManager());

        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);

        return manager;
    }

    /**
     * 配置自定义的权限登录器
     *
     * @return
     */
    @Bean(name = "authRealm")
    public AuthRealm authRealm() {
        AuthRealm authRealm = new AuthRealm();
        //认证缓存
        authRealm.setAuthenticationCacheName(StaticConfig.AUTHENTICATIONCACHE_NAME);
        authRealm.setAuthenticationCachingEnabled(true);

        //授权缓存
        authRealm.setAuthorizationCacheName(StaticConfig.AUTHORIZATIONCACHE_NAME);
        authRealm.setAuthorizationCachingEnabled(true);
        return authRealm;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }
}
