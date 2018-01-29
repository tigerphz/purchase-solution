package com.zixun.purchase.service;

import com.github.pagehelper.PageInfo;
import com.zixun.purchase.core.shiro.DynamicPermsManager;
import com.zixun.purchase.core.spring.SpringUtil;
import com.zixun.purchase.domain.PermissionDomain;
import com.zixun.purchase.model.PermissionInfo;
import com.zixun.purchase.model.bo.MenuBO;
import com.zixun.purchase.model.query.PermissionParam;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-12 下午11:42
 * @version: V1.0
 * @modified by:
 */
@Service
public class PermissionService {
    @Autowired
    private PermissionDomain permissionDomain;

    @Autowired
    private DynamicPermsManager dynamicPermsManager;

    public Boolean insert(PermissionInfo permissionInfo) {
        return permissionDomain.insert(permissionInfo);
    }

    public PageInfo<PermissionInfo> selectByCondition(PermissionParam param) {
        return permissionDomain.selectByCondition(param);
    }

    public List<MenuBO> selectAllPermTree(PermissionParam param) {
        return permissionDomain.selectAllPermTree(param);
    }

    public Boolean updateByPrimaryKeySelective(PermissionInfo permissionInfo) {
        return permissionDomain.updateByPrimaryKeySelective(permissionInfo);
    }

    public List<MenuBO> getMenuByUserId(long userId) {
        return permissionDomain.getMenuByUserId(userId);
    }

    /**
     * 修改了数据库里权限后需要动态刷新权限才能生效
     *
     * @return
     */
    public Boolean dynamicFlashPerms() {
        return dynamicPermsManager.updatePermission();
    }

    public List<PermissionInfo> selectMenuNode() {
        return permissionDomain.selectMenuNode();
    }

    public List<PermissionInfo> selectByRoleId(Long roleId) {
        return permissionDomain.selectByRoleId(roleId);
    }

    public Boolean insertRolePermRelation(Long roleId, List<Long> permIds) {
        return permissionDomain.insertRolePermRelation(roleId, permIds);
    }
}
