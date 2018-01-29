package com.zixun.purchase.persist.daomapper;

import com.zixun.purchase.model.RoleInfo;
import com.zixun.purchase.model.query.RoleParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RoleInfo record);

    int insertSelective(RoleInfo record);

    RoleInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RoleInfo record);

    int updateByPrimaryKey(RoleInfo record);

    List<RoleInfo> selectByCondition(RoleParam param);

    List<RoleInfo> selectByUserId(Long userId);

    List<RoleInfo> selectByUserName(String userName);

    Boolean deleteUserRoleRelationByUserId(Long userId);

    Boolean insertUserRoleRelation(@Param("userId") Long userId, @Param("roleId") Long roleId);
}