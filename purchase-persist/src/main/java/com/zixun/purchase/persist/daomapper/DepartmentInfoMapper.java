package com.zixun.purchase.persist.daomapper;

import com.github.pagehelper.Page;
import com.zixun.purchase.model.DepartmentInfo;
import com.zixun.purchase.model.query.DepartmentParam;

public interface DepartmentInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DepartmentInfo record);

    int insertSelective(DepartmentInfo record);

    DepartmentInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DepartmentInfo record);

    int updateByPrimaryKey(DepartmentInfo record);

    Page<DepartmentInfo> selectByCondition(DepartmentParam param);
}