package com.zixun.purchase.web.controllers;

import com.github.pagehelper.PageInfo;
import com.zixun.purchase.core.shiro.TokenManager;
import com.zixun.purchase.model.DepartmentInfo;
import com.zixun.purchase.model.RoleInfo;
import com.zixun.purchase.model.query.DepartmentParam;
import com.zixun.purchase.model.vo.DepartmentVO;
import com.zixun.purchase.model.vo.ResultBean;
import com.zixun.purchase.model.vo.RoleVO;
import com.zixun.purchase.service.DepartmentService;
import com.zixun.purchase.utils.security.SnowflakeIdWorker;
import com.zixun.purchase.web.mappers.DepartmentMapper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-14 上午11:11
 * @version: V1.0
 * @modified by:
 */
@RestController
@RequestMapping("/api/departments")
@Api("部门信息相关API")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ApiOperation("获取所有权限信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "param", dataType = "DepartmentParam", value = "获取所有权限信息")
    })
    public ResultBean<PageInfo<DepartmentInfo>> list(DepartmentParam param) {
        PageInfo<DepartmentInfo> permissionInfoPageInfos = departmentService.selectByCondition(param);

        ResultBean<PageInfo<DepartmentInfo>> resultBean = new ResultBean<PageInfo<DepartmentInfo>>(permissionInfoPageInfos);

        return resultBean;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ApiOperation("添加部门信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "department", dataType = "DepartmentVO", value = "部门信息")
    })
    public ResultBean<Boolean> insertDepartment(@Valid @RequestBody() DepartmentVO departmentVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getFieldError().getDefaultMessage();
            return new ResultBean<>(message);
        }

        DepartmentInfo permissionInfo = departmentMapper.to(departmentVO);
        permissionInfo.setId(snowflakeIdWorker.nextId());

        Date date = new Date();
        permissionInfo.setCreatedate(date);
        permissionInfo.setCreateusername(TokenManager.getUserName());
        permissionInfo.setModifydate(date);
        permissionInfo.setModifyusername(TokenManager.getUserName());

        ResultBean<Boolean> result = new ResultBean<>(departmentService.insert(permissionInfo));

        return result;
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ApiOperation("更新部门信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "roleVO", dataType = "RoleVO", value = "角色信息")
    })
    public ResultBean<Boolean> update(@Valid @RequestBody() DepartmentVO departmentVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getFieldError().getDefaultMessage();
            return new ResultBean<>(message);
        }

        DepartmentInfo departmentInfo = departmentMapper.to(departmentVO);
        Date date = new Date();
        departmentInfo.setModifydate(date);
        departmentInfo.setModifyusername(TokenManager.getUserName());

        Boolean isSucc = departmentService.updateByPrimaryKeySelective(departmentInfo);

        ResultBean<Boolean> resultBean = new ResultBean<>(isSucc);
        if (!isSucc) {
            resultBean.setFailMessage("更新部门失败");
        }

        return resultBean;
    }

    @RequestMapping(value = "status", method = RequestMethod.POST)
    @ApiOperation(value = "更新部门状态", responseHeaders = @ResponseHeader(name = "Content-type", description = "application/x-www-form-urlencoded"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "Long", value = "Id", required = true),
            @ApiImplicitParam(paramType = "query", name = "status", dataType = "Long", value = "状态", required = true)
    })
    public ResultBean<Boolean> updateStatus(@RequestParam(value = "id") Long id, @RequestParam(value = "status") Integer status) {
        ResultBean<Boolean> resultBean = new ResultBean<>();

        DepartmentInfo departmentInfo = new DepartmentInfo();
        departmentInfo.setId(id);
        departmentInfo.setStatus(status);

        Boolean isSucc = departmentService.updateByPrimaryKeySelective(departmentInfo);
        resultBean.setData(isSucc);

        if (!isSucc) {
            resultBean.setFailMessage("更新部门状态失败");
        }

        return resultBean;
    }
}
