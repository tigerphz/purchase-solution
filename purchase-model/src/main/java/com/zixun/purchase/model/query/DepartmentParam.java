package com.zixun.purchase.model.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-14 上午10:42
 * @version: V1.0
 * @modified by:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DepartmentParam extends BaseQueryCondition {
    private String deptname;

    private Integer status;
}
