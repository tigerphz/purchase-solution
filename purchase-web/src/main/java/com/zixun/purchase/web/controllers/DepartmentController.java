package com.zixun.purchase.web.controllers;

import com.github.pagehelper.PageInfo;
import com.zixun.purchase.model.DepartmentInfo;
import com.zixun.purchase.model.query.DepartmentParam;
import com.zixun.purchase.model.vo.DepartmentVO;
import com.zixun.purchase.model.vo.ResultBean;
import com.zixun.purchase.service.DepartmentService;
import com.zixun.purchase.web.mappers.DepartmentMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ApiOperation("获取所有权限信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "param", dataType = "DepartmentParam", value = "获取所有权限信息")
    })
    public ResultBean<PageInfo<DepartmentInfo>> list(DepartmentParam param) {
        PageInfo<DepartmentInfo> permissionInfoPageInfos = departmentService.selectByCondition(param);

        ResultBean<PageInfo<DepartmentInfo>> resultBean = new ResultBean<PageInfo<DepartmentInfo>>(permissionInfoPageInfos);

        return resultBean;
    }

    @RequestMapping(value = "add", method = RequestMethod.PUT)
    @ApiOperation("添加部门信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "department", dataType = "DepartmentVO", value = "部门信息")
    })
    public ResultBean<Boolean> insertDepartment(@RequestParam("department") DepartmentVO departmentVO) {
        DepartmentInfo departmentInfo = departmentMapper.to(departmentVO);

        ResultBean<Boolean> result = new ResultBean<Boolean>(departmentService.insert(departmentInfo));

        return result;
    }
}
