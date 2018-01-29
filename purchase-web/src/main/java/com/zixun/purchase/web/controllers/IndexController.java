package com.zixun.purchase.web.controllers;

import com.zixun.purchase.core.shiro.AuthRealm;
import com.zixun.purchase.core.shiro.TokenManager;
import com.zixun.purchase.model.UserInfo;
import com.zixun.purchase.model.enums.UserStatusEnum;
import com.zixun.purchase.model.vo.LoginVO;
import com.zixun.purchase.model.vo.ResultBean;
import com.zixun.purchase.model.vo.UserVO;
import com.zixun.purchase.service.UserService;
import com.zixun.purchase.utils.jwt.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-11 上午9:55
 * @version: V1.0
 * @modified by:
 */
@RestController
@RequestMapping("/api")
@Api("注册登录相关API")
public class IndexController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "userVO", required = true, dataType = "LoginVO", value = "用户账号密码"),
    })
    public ResultBean<String> login(@Valid @RequestBody LoginVO userVO, BindingResult bindingResult) {
        ResultBean<String> resultBean = new ResultBean<>();

        if (bindingResult.hasErrors()) {
            String message = bindingResult.getFieldError().getDefaultMessage();
            resultBean.setMessage(message);
            resultBean.setStatus(ResultBean.FAIL);

            return resultBean;
        }

        UserInfo userInfo = userService.selectByUserName(userVO.getUsername());

        if (null == userInfo) {
            throw new UnknownAccountException("该用户不存在");
        } else if (userInfo.getStatus().equals(UserStatusEnum.DELETE.getCode())) {
            throw new DisabledAccountException("帐号已经删除,不能使用");
        } else if (userInfo.getStatus().equals(UserStatusEnum.LOCKED.getCode())) {
            throw new LockedAccountException("帐号已经锁定,不能使用");
        }

        String md5Pwd = new Md5Hash(userVO.getPassword(), userInfo.getPasswordsalt(), AuthRealm.HASHITERATIONS).toString();

        if (md5Pwd.equals(userInfo.getPasswordhash())) {
            String token = JwtUtil.sign(userInfo.getId(), userInfo.getUsername(), userInfo.getPasswordhash());
            resultBean.setData(token);
        } else {
            throw new IncorrectCredentialsException("账号密码不正确");
        }

        return resultBean;
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    @ApiOperation("用户退出")
    public ResultBean<Boolean> login() {
        ResultBean<Boolean> resultBean = new ResultBean<>(Boolean.TRUE);
        TokenManager.logout();

        return resultBean;
    }

    @RequestMapping("/401")
    public ResultBean<Boolean> Unauthorized() {
        ResultBean<Boolean> resultBean = new ResultBean<>();
        resultBean.setFailMessage("您没有得到授权进行该操作");
        return resultBean;
    }
}
