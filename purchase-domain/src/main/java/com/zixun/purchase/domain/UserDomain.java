package com.zixun.purchase.domain;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zixun.purchase.model.PermissionInfo;
import com.zixun.purchase.model.RoleInfo;
import com.zixun.purchase.model.UserInfo;
import com.zixun.purchase.model.bo.LoginedUserBO;
import com.zixun.purchase.model.query.UserParam;
import com.zixun.purchase.persist.daorepository.PermissionRepository;
import com.zixun.purchase.persist.daorepository.RoleRepository;
import com.zixun.purchase.persist.daorepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-10 下午10:16
 * @version: V1.0
 * @modified by:
 */
@Service
public class UserDomain {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    public Boolean insert(UserInfo user) {
        return userRepository.insert(user);
    }

    public Boolean insertSelective(UserInfo record) {
        return userRepository.insertSelective(record);
    }

    public boolean updateByIdSelective(UserInfo record) {
        return userRepository.updateByIdSelective(record);
    }

    public UserInfo selectByUserName(String userName) {
        return userRepository.selectByUserName(userName);
    }

    public UserInfo selectByUserId(Long userId) {
        return userRepository.selectByPrimaryKey(userId);
    }

    public PageInfo<UserInfo> selectByCondition(UserParam param) {
        if (null != param.getPageNum() && null != param.getPageSize()) {
            PageHelper.startPage(param.getPageNum(), param.getPageSize());
        }

        List<UserInfo> userInfos = userRepository.selectByCondition(param);
        PageInfo<UserInfo> userInfoPageInfo = new PageInfo<>(userInfos);

        return userInfoPageInfo;
    }

    /**
     * 获取登录用户基本信息以及角色权限信息
     *
     * @param userName
     * @return
     */
    public LoginedUserBO getLoginedUserBO(String userName) {
        UserInfo userInfo = userRepository.selectByUserName(userName);
        LoginedUserBO loginedUserBO = new LoginedUserBO();
        loginedUserBO.setId(userInfo.getId());
        loginedUserBO.setUsername(userInfo.getUsername());
        loginedUserBO.setNickname(userInfo.getNickname());

        List<RoleInfo> roleInfos = roleRepository.selectByUserName(userName);
        loginedUserBO.setRoles(roleInfos.stream().map(x -> x.getRolename()).collect(Collectors.toList()));

        List<PermissionInfo> permissionInfos = permissionRepository.selectByUserName(userName);
        loginedUserBO.setPermissions(permissionInfos.stream().map(x -> x.getCode()).collect(Collectors.toList()));

        return loginedUserBO;
    }
}
