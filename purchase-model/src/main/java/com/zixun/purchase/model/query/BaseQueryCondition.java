package com.zixun.purchase.model.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-13 上午12:05
 * @version: V1.0
 * @modified by:
 */
@Data
@EqualsAndHashCode
public class BaseQueryCondition {

    public Integer PageNum;

    public Integer PageSize;
}
