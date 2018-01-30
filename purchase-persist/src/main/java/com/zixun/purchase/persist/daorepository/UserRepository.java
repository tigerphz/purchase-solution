package com.zixun.purchase.persist.daorepository;

import com.zixun.purchase.model.UserInfo;
import com.zixun.purchase.model.query.UserParam;
import com.zixun.purchase.persist.daomapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-10 下午10:16
 * @version: V1.0
 * @modified by:
 */
@Repository
public class UserRepository {
    @Autowired
    private UserInfoMapper userInfoMapper;

    public boolean deleteByPrimaryKey(Long id) {
        return userInfoMapper.deleteByPrimaryKey(id) > 0;
    }

    public boolean insert(UserInfo record) {
        return userInfoMapper.insert(record) > 0;
    }

    public boolean insertSelective(UserInfo record) {
        return userInfoMapper.insertSelective(record) > 0;
    }

    public UserInfo selectByPrimaryKey(Long id) {
        return userInfoMapper.selectByPrimaryKey(id);
    }

    public boolean updateByIdSelective(UserInfo record) {
        return userInfoMapper.updateByPrimaryKeySelective(record) > 0;
    }

    public boolean updateByPrimaryKey(UserInfo record) {
        return userInfoMapper.updateByPrimaryKey(record) > 0;
    }

    public UserInfo selectByUserName(String userName) {
        return userInfoMapper.selectByUserName(userName);
    }

    public List<UserInfo> selectByCondition(UserParam param) {
        return userInfoMapper.selectByCondition(param);
    }
}
