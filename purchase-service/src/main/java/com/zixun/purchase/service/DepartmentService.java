package com.zixun.purchase.service;

import com.github.pagehelper.PageInfo;
import com.zixun.purchase.domain.DepartmentDomain;
import com.zixun.purchase.model.DepartmentInfo;
import com.zixun.purchase.model.query.DepartmentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-14 上午11:07
 * @version: V1.0
 * @modified by:
 */
@Service
public class DepartmentService {
    @Autowired
    private DepartmentDomain departmentDomain;

    public boolean insert(DepartmentInfo departmentInfo) {
        return departmentDomain.insert(departmentInfo);
    }

    public PageInfo<DepartmentInfo> selectByCondition(DepartmentParam param) {
        return departmentDomain.selectByCondition(param);
    }
}
