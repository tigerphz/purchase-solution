package com.zixun.purchase.web.mappers;

import com.zixun.purchase.model.PermissionInfo;
import com.zixun.purchase.domain.mappers.BasicObjectMapper;
import com.zixun.purchase.model.vo.PermissionVO;
import org.mapstruct.Mapper;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-13 下午1:09
 * @version: V1.0
 * @modified by:
 */
@Mapper(componentModel = "spring")
public interface PermissionMapper extends BasicObjectMapper<PermissionVO,PermissionInfo> {
}