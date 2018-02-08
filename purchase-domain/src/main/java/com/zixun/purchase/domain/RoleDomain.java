package com.zixun.purchase.domain;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zixun.purchase.core.shiro.TokenManager;
import com.zixun.purchase.model.RoleInfo;
import com.zixun.purchase.model.query.RoleParam;
import com.zixun.purchase.persist.daorepository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-13 下午1:20
 * @version: V1.0
 * @modified by:
 */
@Service
public class RoleDomain {
    @Autowired
    private RoleRepository roleRepository;

    public boolean insert(RoleInfo roleInfo) {
        return roleRepository.insert(roleInfo);
    }

    public PageInfo<RoleInfo> selectByCondition(RoleParam param) {
        if (null != param.getPageNum() && null != param.getPageSize()) {
            PageHelper.startPage(param.getPageNum(), param.getPageSize());
        }

        List<RoleInfo> roleInfos = roleRepository.selectByCondition(param);
        PageInfo<RoleInfo> roleInfoPageInfo = new PageInfo<RoleInfo>(roleInfos);

        return roleInfoPageInfo;
    }

    /**
     * 根据用户ID获取用户所有的权限
     *
     * @param userId
     * @return
     */
    public List<RoleInfo> selectByUserId(Long userId) {
        return roleRepository.selectByUserId(userId);
    }

    public Boolean updateByPrimaryKeySelective(RoleInfo roleInfo) {
        return roleRepository.updateByPrimaryKeySelective(roleInfo) > 0;
    }

    /**
     * 插入用户角色关系表
     *
     * @param userId
     * @param roleIds
     * @return
     */
    public Boolean insertUserRoleRelation(Long userId, List<Long> roleIds) {
        //清理授权缓存
        TokenManager.ClearAuthorizationCache();

        return roleRepository.insertUserRoleRelation(userId, roleIds);
    }
}
