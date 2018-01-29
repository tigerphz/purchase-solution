package com.zixun.purchase.persist.daorepository;

import com.zixun.purchase.model.RoleInfo;
import com.zixun.purchase.model.query.RoleParam;
import com.zixun.purchase.persist.daomapper.RoleInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-12 下午11:22
 * @version: V1.0
 * @modified by:
 */
@Repository
public class RoleRepository {
    @Autowired
    private RoleInfoMapper roleInfoMapper;

    public boolean deleteByPrimaryKey(Long id) {
        return roleInfoMapper.deleteByPrimaryKey(id) > 0;
    }

    public boolean insert(RoleInfo record) {
        return roleInfoMapper.insert(record) > 0;
    }

    public boolean insertSelective(RoleInfo record) {
        return roleInfoMapper.insertSelective(record) > 0;
    }

    public RoleInfo selectByPrimaryKey(Long id) {
        return roleInfoMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(RoleInfo record) {
        return roleInfoMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(RoleInfo record) {
        return roleInfoMapper.updateByPrimaryKey(record);
    }

    public List<RoleInfo> selectByCondition(RoleParam param) {
        return roleInfoMapper.selectByCondition(param);
    }

    public List<RoleInfo> selectByUserId(Long userId) {
        return roleInfoMapper.selectByUserId(userId);
    }

    public List<RoleInfo> selectByUserName(String userName) {
        return roleInfoMapper.selectByUserName(userName);
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean insertUserRoleRelation(Long userId, List<Long> roleIds) {
        roleInfoMapper.deleteUserRoleRelationByUserId(userId);
        for (Long roleId : roleIds) {
            roleInfoMapper.insertUserRoleRelation(userId, roleId);
        }

        return true;
    }
}
