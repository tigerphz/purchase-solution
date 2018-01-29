package com.zixun.purchase.domain.mappers;

import com.zixun.purchase.model.PermissionInfo;
import com.zixun.purchase.model.bo.MenuBO;
import org.mapstruct.Mapper;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-26 上午10:47
 * @version: V1.0
 * @modified by:
 */
@Mapper(componentModel = "spring")
public interface MenuBoMapper extends BasicObjectMapper<MenuBO, PermissionInfo> {
}
