package com.zixun.purchase.persist.daomapper;

import com.github.pagehelper.Page;
import com.zixun.purchase.model.UserInfo;
import com.zixun.purchase.model.bo.LoginedUserBO;
import com.zixun.purchase.model.query.UserParam;

import java.util.List;

public interface UserInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    UserInfo selectByUserName(String userName);

    List<UserInfo> selectByCondition(UserParam param);
}