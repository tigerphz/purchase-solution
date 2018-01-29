package com.zixun.purchase.web.mappers;

import com.zixun.purchase.model.DepartmentInfo;
import com.zixun.purchase.domain.mappers.BasicObjectMapper;
import com.zixun.purchase.model.vo.DepartmentVO;
import org.mapstruct.Mapper;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-14 上午11:13
 * @version: V1.0
 * @modified by:
 */
@Mapper(componentModel = "spring")
public interface DepartmentMapper extends BasicObjectMapper<DepartmentVO,DepartmentInfo> {
}
