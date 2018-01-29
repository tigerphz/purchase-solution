package com.zixun.purchase.persist.daomapper;

import com.zixun.purchase.model.PermissionInfo;
import com.zixun.purchase.model.query.PermissionParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PermissionInfo record);

    int insertSelective(PermissionInfo record);

    PermissionInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PermissionInfo record);

    int updateByPrimaryKey(PermissionInfo record);

    List<PermissionInfo> selectByUserId(Long userId);

    List<PermissionInfo> selectByUserName(String userName);

    List<PermissionInfo> selectByCondition(PermissionParam param);

    List<PermissionInfo> selectShiroAllPermission();

    List<PermissionInfo> selectShiroPermissionByUserId(Long userId);

    List<PermissionInfo> selectMenuNode();

    List<PermissionInfo> selectByRoleId(Long roleId);

    Boolean deleteRolePermRelationByRoleId(Long roleId);

    Boolean insertRolePermRelation(@Param("roleId") Long roleId, @Param("permId") Long permId);
}