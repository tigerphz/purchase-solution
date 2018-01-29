package com.zixun.purchase.web.controllers;

import com.github.pagehelper.PageInfo;
import com.zixun.purchase.core.shiro.AuthRealm;
import com.zixun.purchase.core.shiro.TokenManager;
import com.zixun.purchase.domain.UserDomain;
import com.zixun.purchase.model.UserInfo;
import com.zixun.purchase.model.bo.LoginedUserBO;
import com.zixun.purchase.model.enums.UserStatusEnum;
import com.zixun.purchase.model.query.UserParam;
import com.zixun.purchase.model.vo.ResultBean;
import com.zixun.purchase.model.vo.UserVO;
import com.zixun.purchase.service.UserService;
import com.zixun.purchase.utils.security.SnowflakeIdWorker;
import com.zixun.purchase.web.mappers.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-10 下午2:33
 * @version: V1.0
 * @modified by:
 */
@RestController
@RequestMapping("api/users")
@Api("用户信息相关API")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ApiOperation("获取所有用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "param", dataType = "UserParam", value = "查询条件")
    })
    public ResultBean<PageInfo<UserVO>> list(UserParam param) {
        PageInfo<UserInfo> userInfoPageInfo = userService.selectByCondition(param);

        List<UserVO> userList = userMapper.from(userInfoPageInfo.getList());
        PageInfo<UserVO> userVOPageInfo = new PageInfo<>(userList);

        ResultBean<PageInfo<UserVO>> resultBean = new ResultBean<>(userVOPageInfo);

        return resultBean;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ApiOperation("添加用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "user", dataType = "UserVO", value = "用户信息")
    })
    public ResultBean<Boolean> add(@Valid @RequestBody() UserVO userVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getFieldError().getDefaultMessage();
            return new ResultBean<>(message);
        }
        if (StringUtils.isEmpty(userVO.getPassword())) {
            return new ResultBean<>("密码不能为空");
        }

        UserInfo userInfo = userService.selectByUserName(userVO.getUsername());
        if (userInfo != null) {
            return new ResultBean<>("该用户名已经存在");
        }

        String salt = UUID.randomUUID().toString();
        String md5pwd = new Md5Hash(userVO.getPassword(), salt, AuthRealm.HASHITERATIONS).toString();

        userInfo = userMapper.to(userVO);
        userInfo.setId(snowflakeIdWorker.nextId());
        userInfo.setPasswordhash(md5pwd);
        userInfo.setPasswordsalt(salt);

        Date date = new Date();
        userInfo.setCreatedate(date);
        userInfo.setCreateusername(TokenManager.getUserName());
        userInfo.setModifydate(date);
        userInfo.setModifyusername(TokenManager.getUserName());

        Boolean isSucc = userService.insert(userInfo);

        ResultBean<Boolean> resultBean = new ResultBean<>(isSucc);
        if (!isSucc) {
            resultBean.setFailMessage("插入用户失败");
        }

        return resultBean;
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ApiOperation("更新用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "userVO", dataType = "UserVO", value = "用户信息")
    })
    public ResultBean<Boolean> update(@Valid @RequestBody() UserVO userVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getFieldError().getDefaultMessage();
            return new ResultBean<>(message);
        }

        UserInfo userInfo = userMapper.to(userVO);
        Date date = new Date();
        userInfo.setModifydate(date);
        userInfo.setModifyusername(TokenManager.getUserName());

        Boolean isSucc = userService.updateByPrimaryKeySelective(userInfo);

        ResultBean<Boolean> resultBean = new ResultBean<>(isSucc);
        if (!isSucc) {
            resultBean.setFailMessage("更新用户失败");
        }

        return resultBean;
    }

    @RequestMapping(value = "logininfo", method = RequestMethod.GET)
    @ApiOperation("获取登录用户角色权限信息")
    public ResultBean<LoginedUserBO> getLoginedUserRolesPerms() {
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        LoginedUserBO loginedUserBO = userService.getLoginedUserBO(userInfo.getUsername());

        ResultBean<LoginedUserBO> resultBean = new ResultBean<>(loginedUserBO);

        return resultBean;
    }

    @RequestMapping(value = "status", method = RequestMethod.POST)
    @ApiOperation("更新用户状态")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "Long", value = "Id", required = true),
            @ApiImplicitParam(paramType = "query", name = "status", dataType = "Long", value = "状态", required = true)
    })
    public ResultBean<Boolean> updateStatus(@RequestParam(value = "id") Long id, @RequestParam(value = "status") Integer status) {
        ResultBean<Boolean> resultBean = new ResultBean<>();

        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setStatus(status);

        Boolean isSucc = userService.updateByPrimaryKeySelective(userInfo);
        resultBean.setData(isSucc);

        if (!isSucc) {
            resultBean.setFailMessage("更新用户状态失败");
        }

        return resultBean;
    }
}
