package com.zixun.purchase.core.shiro;

import com.zixun.purchase.model.PermissionInfo;
import com.zixun.purchase.model.RoleInfo;
import com.zixun.purchase.model.UserInfo;
import com.zixun.purchase.persist.daorepository.PermissionRepository;
import com.zixun.purchase.persist.daorepository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-12 上午11:31
 * @version: V1.0
 * @modified by:
 */
public class AuthRealm extends AbstractUserRealm {
    /**
     * 加盐散列次数
     */
    public final static Integer HASHITERATIONS = 1;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public UserRolesAndPermissions doGetRoleAuthorizationInfo(UserInfo userInfo) {
        List<RoleInfo> roleInfoList = roleRepository.selectByUserId(userInfo.getId());
        List<PermissionInfo> permissionInfoList = permissionRepository.selectShiroPermissionByUserId(userInfo.getId());

        Set<String> roleSet = roleInfoList.stream().map(role -> role.getRolename()).collect(Collectors.toSet());
        Set<String> permSet = permissionInfoList.stream().map(perm -> perm.getCode()).collect(Collectors.toSet());

        UserRolesAndPermissions userRolesAndPermissions = new UserRolesAndPermissions(roleSet, permSet);

        return userRolesAndPermissions;
    }
}
