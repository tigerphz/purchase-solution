package com.zixun.purchase.model.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-14 上午10:56
 * @version: V1.0
 * @modified by:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserParam extends BaseQueryCondition {
    private Long id;

    private String username;

    private String nickname;

    private Integer gender;

    private Integer status;

    private String cellphone;

    private String identitycard;

    private String email;

    private String qq;

    private String address;

    private Long deptid;
}
