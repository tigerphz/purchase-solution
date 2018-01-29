package com.zixun.purchase.core.shiro;

import com.zixun.purchase.model.UserInfo;
import com.zixun.purchase.model.enums.UserStatusEnum;
import com.zixun.purchase.persist.daorepository.UserRepository;
import com.zixun.purchase.utils.jwt.JwtUtil;
import jdk.nashorn.internal.parser.Token;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @description: 抽象AuthorizingRealm
 * @author: tiger
 * @date: 18-1-10 下午10:16
 * @version: V1.0
 * @modified by:
 */
public abstract class AbstractUserRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(AbstractUserRealm.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * 判断此Realm是否支持此Token
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 获取用户角色的权限信息
     *
     * @param userInfo
     * @return
     */
    public abstract UserRolesAndPermissions doGetRoleAuthorizationInfo(UserInfo userInfo);

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken) throws AuthenticationException {
        //UserLoginToken对象用来存放提交的登录信息
        JwtToken jwtToken = (JwtToken) authenticationToken;
        String token = (String) jwtToken.getCredentials();

        String username = JwtUtil.getUsername(token);

        if (!StringUtils.hasText(username)) {
            throw new AccountException("根据接受的token没有获取到用户名信息,请确认token信息是否正确");
        }

        //查出是否有此用户
        UserInfo user = userRepository.selectByUserName(username);

        if (null == user) {
            throw new UnknownAccountException("该用户不存在");
        } else if (user.getStatus().equals(UserStatusEnum.DELETE.getCode())) {
            throw new DisabledAccountException("帐号已经删除,不能使用");
        } else if (user.getStatus().equals(UserStatusEnum.LOCKED.getCode())) {
            throw new LockedAccountException("帐号已经锁定,暂不能使用");
        }

        String userToken = JwtUtil.sign(user.getId(), user.getUsername(), user.getPasswordhash());

        logger.debug("登录认证接受的token:{},生成的token:{}", token, userToken);

        // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
        return new SimpleAuthenticationInfo(user, userToken, getName());
    }

    /**
     * 获取授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Set<String> userRoles = new HashSet<>();
        Set<String> userPermissions = new HashSet<>();
        //从数据库中获取当前登录用户的详细信息
        UserInfo userInfo = TokenManager.getLoginUser();
        if (null != userInfo) {
            UserRolesAndPermissions roleContainer = doGetRoleAuthorizationInfo(userInfo);
            userRoles.addAll(roleContainer.getUserRoles());
            userPermissions.addAll(roleContainer.getUserPermissions());
        } else {
            throw new AuthorizationException();
        }

        //为当前用户设置角色和权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRoles(userRoles);
        authorizationInfo.addStringPermissions(userPermissions);

        return authorizationInfo;
    }

    protected class UserRolesAndPermissions {
        Set<String> userRoles;
        Set<String> userPermissions;

        public UserRolesAndPermissions(Set<String> userRoles, Set<String> userPermissions) {
            this.userRoles = userRoles;
            this.userPermissions = userPermissions;
        }

        public Set<String> getUserRoles() {
            return userRoles;
        }

        public Set<String> getUserPermissions() {
            return userPermissions;
        }
    }
}
