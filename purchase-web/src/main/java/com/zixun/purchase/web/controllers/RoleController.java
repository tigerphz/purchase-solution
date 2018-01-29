package com.zixun.purchase.web.controllers;

import com.github.pagehelper.PageInfo;
import com.zixun.purchase.core.shiro.TokenManager;
import com.zixun.purchase.model.RoleInfo;
import com.zixun.purchase.model.query.RoleParam;
import com.zixun.purchase.model.vo.ResultBean;
import com.zixun.purchase.model.vo.RoleVO;
import com.zixun.purchase.model.vo.UserRolesVO;
import com.zixun.purchase.service.RoleService;
import com.zixun.purchase.utils.security.SnowflakeIdWorker;
import com.zixun.purchase.web.mappers.RoleMapper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-13 下午1:15
 * @version: V1.0
 * @modified by:
 */
@RestController
@RequestMapping("api/roles")
@Api("角色信息相关API")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ApiOperation("获取所有权角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "param", dataType = "RoleParam", value = "获取所有权角色信息")
    })
    public ResultBean<PageInfo<RoleInfo>> list(RoleParam param) {
        PageInfo<RoleInfo> roleInfoPageInfo = roleService.selectByCondition(param);

        ResultBean<PageInfo<RoleInfo>> resultBean = new ResultBean<>(roleInfoPageInfo);

        return resultBean;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ApiOperation("添加角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "role", dataType = "RoleVO", value = "角色信息")
    })
    public ResultBean<Boolean> add(@Valid @RequestBody() RoleVO roleVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getFieldError().getDefaultMessage();
            return new ResultBean<>(message);
        }

        RoleParam param = new RoleParam();
        param.setRolename(roleVO.getRolename());
        PageInfo<RoleInfo> roleInfoPageInfo = roleService.selectByCondition(param);
        if (roleInfoPageInfo.getTotal() > 0) {
            return new ResultBean<>("该角色名已经存在");
        }

        RoleInfo roleInfo = roleMapper.to(roleVO);
        roleInfo.setId(snowflakeIdWorker.nextId());

        Date date = new Date();
        roleInfo.setCreatedate(date);
        roleInfo.setCreateusername(TokenManager.getUserName());
        roleInfo.setModifydate(date);
        roleInfo.setModifyusername(TokenManager.getUserName());

        ResultBean<Boolean> result = new ResultBean<>(roleService.insert(roleInfo));

        return result;
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ApiOperation("更新角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "roleVO", dataType = "RoleVO", value = "角色信息")
    })
    public ResultBean<Boolean> update(@Valid @RequestBody() RoleVO roleVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getFieldError().getDefaultMessage();
            return new ResultBean<>(message);
        }

        RoleInfo roleInfo = roleMapper.to(roleVO);
        Date date = new Date();
        roleInfo.setModifydate(date);
        roleInfo.setModifyusername(TokenManager.getUserName());

        Boolean isSucc = roleService.updateByPrimaryKeySelective(roleInfo);

        ResultBean<Boolean> resultBean = new ResultBean<>(isSucc);
        if (!isSucc) {
            resultBean.setFailMessage("更新角色失败");
        }

        return resultBean;
    }

    @RequestMapping(value = "status", method = RequestMethod.POST)
    @ApiOperation(value = "更新角色状态", responseHeaders = @ResponseHeader(name = "Content-type", description = "application/x-www-form-urlencoded"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "Long", value = "Id", required = true),
            @ApiImplicitParam(paramType = "query", name = "status", dataType = "Long", value = "状态", required = true)
    })
    public ResultBean<Boolean> updateStatus(@RequestParam(value = "id") Long id, @RequestParam(value = "status") Integer status) {
        ResultBean<Boolean> resultBean = new ResultBean<>();

        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setId(id);
        roleInfo.setStatus(status);

        Boolean isSucc = roleService.updateByPrimaryKeySelective(roleInfo);
        resultBean.setData(isSucc);

        if (!isSucc) {
            resultBean.setFailMessage("更新角色状态失败");
        }

        return resultBean;
    }

    @RequestMapping(value = "getUserRoles", method = RequestMethod.GET)
    @ApiOperation("获取用户拥有的角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", dataType = "Long", value = "用户Id", required = true),
    })
    public ResultBean<List<RoleInfo>> getUserRoles(Long userId) {
        ResultBean<List<RoleInfo>> resultBean = new ResultBean<>();
        List<RoleInfo> roleInfos = roleService.selectByUserId(userId);
        resultBean.setData(roleInfos);

        return resultBean;
    }

    @RequestMapping(value = "allRoles", method = RequestMethod.GET)
    @ApiOperation("获取所有角色")
    @ApiImplicitParams({
    })
    public ResultBean<List<RoleInfo>> getAllRoles() {
        ResultBean<List<RoleInfo>> resultBean = new ResultBean<>();

        RoleParam param = new RoleParam();
        param.setStatus(0);
        List<RoleInfo> roleInfos = roleService.selectByCondition(param).getList();
        resultBean.setData(roleInfos);

        return resultBean;
    }

    @RequestMapping(value = "updateUserRoles", method = RequestMethod.POST)
    @ApiOperation(value = "更新用户拥有的角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "userRolesVO", dataType = "UserRolesVO", value = "用户Id与角色Id关系信息", required = true),
    })
    public ResultBean<Boolean> updateUserRoles(@Valid @RequestBody UserRolesVO userRolesVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getFieldError().getDefaultMessage();
            return new ResultBean<>(message);
        }

        ResultBean<Boolean> resultBean = new ResultBean<>();

        boolean isSucc = roleService.insertUserRoleRelation(userRolesVO.getUserId(), userRolesVO.getRoleIds());
        resultBean.setData(isSucc);

        return resultBean;
    }
}
