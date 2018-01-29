package com.zixun.purchase.persist.daorepository;

import com.zixun.purchase.model.DepartmentInfo;
import com.zixun.purchase.model.query.DepartmentParam;
import com.zixun.purchase.persist.daomapper.DepartmentInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-14 上午10:44
 * @version: V1.0
 * @modified by:
 */
@Repository
public class DepartmentRepository {
    @Autowired
    private DepartmentInfoMapper departmentInfoMapper;

    public boolean deleteByPrimaryKey(Long id) {
        return departmentInfoMapper.deleteByPrimaryKey(id) > 0;
    }

    public boolean insert(DepartmentInfo record) {
        return departmentInfoMapper.insert(record) > 0;
    }

    public boolean insertSelective(DepartmentInfo record) {
        return departmentInfoMapper.insertSelective(record) > 0;
    }

    public DepartmentInfo selectByPrimaryKey(Long id) {
        return departmentInfoMapper.selectByPrimaryKey(id);
    }

    public boolean updateByPrimaryKeySelective(DepartmentInfo record) {
        return departmentInfoMapper.updateByPrimaryKeySelective(record) > 0;
    }

    public boolean updateByPrimaryKey(DepartmentInfo record) {
        return departmentInfoMapper.updateByPrimaryKey(record) > 0;
    }

    public List<DepartmentInfo> selectByCondition(DepartmentParam param) {
        return departmentInfoMapper.selectByCondition(param);
    }
}
