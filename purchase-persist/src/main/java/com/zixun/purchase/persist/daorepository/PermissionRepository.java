package com.zixun.purchase.persist.daorepository;

import com.zixun.purchase.model.PermissionInfo;
import com.zixun.purchase.model.query.PermissionParam;
import com.zixun.purchase.persist.daomapper.PermissionInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-12 下午10:33
 * @version: V1.0
 * @modified by:
 */
@Repository
public class PermissionRepository {
    @Autowired
    private PermissionInfoMapper permissionInfoMapper;

    public boolean deleteByPrimaryKey(Long id) {
        return permissionInfoMapper.deleteByPrimaryKey(id) > 0;
    }

    public boolean insert(PermissionInfo record) {
        return permissionInfoMapper.insert(record) > 0;
    }

    public boolean insertSelective(PermissionInfo record) {
        return permissionInfoMapper.insertSelective(record) > 0;
    }

    public PermissionInfo selectByPrimaryKey(Long id) {
        return permissionInfoMapper.selectByPrimaryKey(id);
    }

    public boolean updateByPrimaryKeySelective(PermissionInfo record) {
        return permissionInfoMapper.updateByPrimaryKeySelective(record) > 0;
    }

    public boolean updateByPrimaryKey(PermissionInfo record) {
        return permissionInfoMapper.updateByPrimaryKey(record) > 0;
    }

    public List<PermissionInfo> selectByUserId(Long userId) {
        return permissionInfoMapper.selectByUserId(userId);
    }

    public List<PermissionInfo> selectByUserName(String userName) {
        return permissionInfoMapper.selectByUserName(userName);
    }

    public List<PermissionInfo> selectByCondition(PermissionParam param) {
        return permissionInfoMapper.selectByCondition(param);
    }

    /**
     * 获取所有页面按钮权限
     *
     * @return
     */
    public List<PermissionInfo> selectShiroAllPermission() {
        return permissionInfoMapper.selectShiroAllPermission();
    }

    /**
     * 获取所有页面按钮权限
     *
     * @return
     */
    public List<PermissionInfo> selectShiroPermissionByUserId(long userId) {
        return permissionInfoMapper.selectShiroPermissionByUserId(userId);
    }

    /**
     * 获取所有节点数据不包括按钮
     *
     * @return
     */
    public List<PermissionInfo> selectMenuNode() {
        return permissionInfoMapper.selectMenuNode();
    }

    public List<PermissionInfo> selectByRoleId(Long roleId) {
        return permissionInfoMapper.selectByRoleId(roleId);
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean insertRolePermRelation(Long roleId, List<Long> permIds) {
        permissionInfoMapper.deleteRolePermRelationByRoleId(roleId);
        for (Long permId : permIds) {
            permissionInfoMapper.insertRolePermRelation(roleId, permId);
        }

        return true;
    }
}
