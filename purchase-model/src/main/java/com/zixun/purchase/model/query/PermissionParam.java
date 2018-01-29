package com.zixun.purchase.model.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-13 上午12:03
 * @version: V1.0
 * @modified by:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PermissionParam extends BaseQueryCondition {
    private String permname;

    private String url;

    private Integer status;
}
