package com.zixun.purchase.service;

import com.github.pagehelper.PageInfo;
import com.zixun.purchase.domain.RoleDomain;
import com.zixun.purchase.model.RoleInfo;
import com.zixun.purchase.model.query.RoleParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-13 下午1:19
 * @version: V1.0
 * @modified by:
 */
@Service
public class RoleService {
    @Autowired
    private RoleDomain roleDomain;

    public boolean insert(RoleInfo roleInfo) {
        return roleDomain.insert(roleInfo);
    }

    public PageInfo<RoleInfo> selectByCondition(RoleParam param) {
        return roleDomain.selectByCondition(param);
    }

    public Boolean updateByPrimaryKeySelective(RoleInfo roleInfo) {
        return roleDomain.updateByPrimaryKeySelective(roleInfo);
    }

    public List<RoleInfo> selectByUserId(Long userId) {
        return roleDomain.selectByUserId(userId);
    }

    public Boolean insertUserRoleRelation(Long userId, List<Long> roleIds) {
        return roleDomain.insertUserRoleRelation(userId, roleIds);
    }
}
