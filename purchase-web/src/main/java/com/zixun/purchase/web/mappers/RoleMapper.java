package com.zixun.purchase.web.mappers;

import com.zixun.purchase.model.RoleInfo;
import com.zixun.purchase.domain.mappers.BasicObjectMapper;
import com.zixun.purchase.model.vo.RoleVO;
import org.mapstruct.Mapper;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-13 下午8:47
 * @version: V1.0
 * @modified by:
 */
@Mapper(componentModel = "spring")
public interface RoleMapper extends BasicObjectMapper<RoleVO, RoleInfo> {
}
