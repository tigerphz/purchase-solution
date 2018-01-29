package com.zixun.purchase.model.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-13 下午8:40
 * @version: V1.0
 * @modified by:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleParam extends BaseQueryCondition {
    private String rolename;

    private Integer status;

    private String description;
}
