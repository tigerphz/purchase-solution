package com.zixun.purchase.persist.daomapper;

import com.zixun.purchase.model.DepartmentInfo;
import com.zixun.purchase.model.query.DepartmentParam;

import java.util.List;

public interface DepartmentInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DepartmentInfo record);

    int insertSelective(DepartmentInfo record);

    DepartmentInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DepartmentInfo record);

    int updateByPrimaryKey(DepartmentInfo record);

    List<DepartmentInfo> selectByCondition(DepartmentParam param);
}