package com.zixun.purchase.web.mappers;

import com.zixun.purchase.model.UserInfo;
import com.zixun.purchase.domain.mappers.BasicObjectMapper;
import com.zixun.purchase.model.vo.UserVO;
import org.mapstruct.Mapper;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-12 下午6:19
 * @version: V1.0
 * @modified by:
 */
@Mapper(componentModel = "spring")
public interface UserMapper extends BasicObjectMapper<UserVO, UserInfo> {
}