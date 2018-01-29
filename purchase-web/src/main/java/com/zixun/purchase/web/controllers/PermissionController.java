package com.zixun.purchase.web.controllers;

import com.github.pagehelper.PageInfo;
import com.zixun.purchase.core.shiro.TokenManager;
import com.zixun.purchase.model.PermissionInfo;
import com.zixun.purchase.model.bo.MenuBO;
import com.zixun.purchase.model.query.PermissionParam;
import com.zixun.purchase.model.vo.PermissionVO;
import com.zixun.purchase.model.vo.ResultBean;
import com.zixun.purchase.model.vo.RolePermsVO;
import com.zixun.purchase.service.PermissionService;
import com.zixun.purchase.utils.security.SnowflakeIdWorker;
import com.zixun.purchase.web.mappers.PermissionMapper;
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
 * @date: 18-1-12 下午10:30
 * @version: V1.0
 * @modified by:
 */
@RestController
@RequestMapping("api/permissions")
@Api("权限信息相关API")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ApiOperation("获取所有权限信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "param", dataType = "PermissionParam", value = "查询条件信息")
    })
    public ResultBean<PageInfo<PermissionInfo>> list(PermissionParam param) {
        PageInfo<PermissionInfo> permissionInfoPageInfos = permissionService.selectByCondition(param);

        ResultBean<PageInfo<PermissionInfo>> resultBean = new ResultBean<>(permissionInfoPageInfos);

        return resultBean;
    }

    @RequestMapping(value = "tree", method = RequestMethod.POST)
    @ApiOperation("获取所有权限树结构信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "param", dataType = "PermissionParam", value = "查询条件信息")
    })
    public ResultBean<List<MenuBO>> tree(PermissionParam param) {
        List<MenuBO> menuBOList = permissionService.selectAllPermTree(param);

        ResultBean<List<MenuBO>> resultBean = new ResultBean<>(menuBOList);

        return resultBean;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ApiOperation("添加权限信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "permission", dataType = "PermissionVO", value = "权限信息")
    })
    public ResultBean<Boolean> add(@Valid @RequestBody() PermissionVO permissionVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getFieldError().getDefaultMessage();
            return new ResultBean<>(message);
        }

        PermissionInfo permissionInfo = permissionMapper.to(permissionVO);
        permissionInfo.setId(snowflakeIdWorker.nextId());

        Date date = new Date();
        permissionInfo.setCreatedate(date);
        permissionInfo.setCreateusername(TokenManager.getUserName());
        permissionInfo.setModifydate(date);
        permissionInfo.setModifyusername(TokenManager.getUserName());

        ResultBean<Boolean> result = new ResultBean<>(permissionService.insert(permissionInfo));

        return result;
    }

    @RequestMapping(value = "menunode", method = RequestMethod.GET)
    @ApiOperation("获取所有菜单节点")
    @ApiImplicitParams({
    })
    public ResultBean<List<PermissionInfo>> menuNode() {
        ResultBean<List<PermissionInfo>> resultBean = new ResultBean<>();
        List<PermissionInfo> permissionInfoList = permissionService.selectMenuNode();
        resultBean.setData(permissionInfoList);

        return resultBean;
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ApiOperation("更新权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "permissionVO", dataType = "PermissionVO", value = "权限信息")
    })
    public ResultBean<Boolean> update(@Valid @RequestBody() PermissionVO permissionVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getFieldError().getDefaultMessage();
            return new ResultBean<>(message);
        }

        PermissionInfo permissionInfo = permissionMapper.to(permissionVO);
        Date date = new Date();
        permissionInfo.setModifydate(date);
        permissionInfo.setModifyusername(TokenManager.getUserName());

        Boolean isSucc = permissionService.updateByPrimaryKeySelective(permissionInfo);

        ResultBean<Boolean> resultBean = new ResultBean<>(isSucc);
        if (!isSucc) {
            resultBean.setFailMessage("更新权限失败");
        }

        return resultBean;
    }

    @RequestMapping(value = "status", method = RequestMethod.POST)
    @ApiOperation("更新权限状态")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "Long", value = "Id", required = true),
            @ApiImplicitParam(paramType = "query", name = "status", dataType = "Long", value = "状态", required = true)
    })
    public ResultBean<Boolean> updateStatus(@RequestParam(value = "id") Long id, @RequestParam(value = "status") Integer status) {
        ResultBean<Boolean> resultBean = new ResultBean<>();

        PermissionInfo permissionInfo = new PermissionInfo();
        permissionInfo.setId(id);
        permissionInfo.setStatus(status);

        Boolean isSucc = permissionService.updateByPrimaryKeySelective(permissionInfo);
        resultBean.setData(isSucc);

        if (!isSucc) {
            resultBean.setFailMessage("更新角色状态失败");
        }

        return resultBean;
    }

    @RequestMapping(value = "menus", method = RequestMethod.GET)
    @ApiOperation("获取用户菜单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "userId", dataType = "Long", value = "用户Id")
    })
    public ResultBean<List<MenuBO>> getMenus(@RequestParam("userId") Long userId) {
        List<MenuBO> menuBOList = permissionService.getMenuByUserId(userId);

        ResultBean<List<MenuBO>> resultBean = new ResultBean<>(menuBOList);

        return resultBean;
    }

    @RequestMapping(value = "flashPerms", method = RequestMethod.GET)
    @ApiOperation("刷新权限")
    public ResultBean<Boolean> flashPerms() {
        Boolean isSucc = permissionService.dynamicFlashPerms();

        ResultBean<Boolean> resultBean = new ResultBean<>(isSucc);

        return resultBean;
    }

    @RequestMapping(value = "getRolePerms", method = RequestMethod.GET)
    @ApiOperation("获取角色拥有的权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "roleId", dataType = "Long", value = "角色Id", required = true),
    })
    public ResultBean<List<PermissionInfo>> getUserPerms(Long roleId) {
        ResultBean<List<PermissionInfo>> resultBean = new ResultBean<>();
        List<PermissionInfo> roleInfos = permissionService.selectByRoleId(roleId);
        resultBean.setData(roleInfos);

        return resultBean;
    }

    @RequestMapping(value = "allPerms", method = RequestMethod.GET)
    @ApiOperation("获取所有权限")
    @ApiImplicitParams({
    })
    public ResultBean<List<MenuBO>> getAllPerms() {
        ResultBean<List<MenuBO>> resultBean = new ResultBean<>();

        PermissionParam param = new PermissionParam();
        param.setStatus(0);
        List<MenuBO> menuBOList = permissionService.selectAllPermTree(param);
        resultBean.setData(menuBOList);

        return resultBean;
    }

    @RequestMapping(value = "updateRolePerms", method = RequestMethod.POST)
    @ApiOperation(value = "更新角色拥有的权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "rolePermsVO", dataType = "RolePermsVO", value = "角色Id与权限Id关系信息", required = true),
    })
    public ResultBean<Boolean> updateRolePerms(@Valid @RequestBody RolePermsVO rolePermsVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getFieldError().getDefaultMessage();
            return new ResultBean<>(message);
        }

        ResultBean<Boolean> resultBean = new ResultBean<>();

        boolean isSucc = permissionService.insertRolePermRelation(rolePermsVO.getRoleId(), rolePermsVO.getPermIds());
        resultBean.setData(isSucc);

        return resultBean;
    }
}
