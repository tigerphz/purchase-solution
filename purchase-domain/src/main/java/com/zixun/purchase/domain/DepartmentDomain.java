package com.zixun.purchase.domain;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zixun.purchase.model.DepartmentInfo;
import com.zixun.purchase.model.query.DepartmentParam;
import com.zixun.purchase.persist.daorepository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-14 上午11:05
 * @version: V1.0
 * @modified by:
 */
@Service
public class DepartmentDomain {
    @Autowired
    private DepartmentRepository departmentRepository;

    public boolean insert(DepartmentInfo departmentInfo) {
        return departmentRepository.insert(departmentInfo);
    }

    public PageInfo<DepartmentInfo> selectByCondition(DepartmentParam param) {
        if (null != param.getPageNum() && null != param.getPageSize()) {
            PageHelper.startPage(param.getPageNum(), param.getPageSize());
        }

        List<DepartmentInfo> departmentInfos = departmentRepository.selectByCondition(param);
        PageInfo<DepartmentInfo> departmentInfoPageInfo = new PageInfo<DepartmentInfo>(departmentInfos);

        return departmentInfoPageInfo;
    }
}
