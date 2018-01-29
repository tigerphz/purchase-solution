package com.zixun.purchase.model.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-13 上午12:05
 * @version: V1.0
 * @modified by:
 */
public class BaseQueryCondition {
    @Getter
    @Setter
    public Integer PageNum;

    @Getter
    @Setter
    public Integer PageSize;
}
