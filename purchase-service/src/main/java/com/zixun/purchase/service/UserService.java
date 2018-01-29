package com.zixun.purchase.service;

import com.github.pagehelper.PageInfo;
import com.zixun.purchase.domain.UserDomain;
import com.zixun.purchase.model.UserInfo;
import com.zixun.purchase.model.bo.LoginedUserBO;
import com.zixun.purchase.model.query.UserParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDomain userDomain;

    public Boolean insert(UserInfo user) {
        return userDomain.insert(user);
    }

    public boolean updateByPrimaryKeySelective(UserInfo record) {
        return userDomain.updateByPrimaryKeySelective(record);
    }

    public UserInfo selectByUserName(String userName) {
        return userDomain.selectByUserName(userName);
    }

    public PageInfo<UserInfo> selectByCondition(UserParam param) {
        return userDomain.selectByCondition(param);
    }

    public LoginedUserBO getLoginedUserBO(String userName) {
        return userDomain.getLoginedUserBO(userName);
    }
}
