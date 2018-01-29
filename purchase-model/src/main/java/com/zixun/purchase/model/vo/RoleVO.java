package com.zixun.purchase.model.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-12 下午11:18
 * @version: V1.0
 * @modified by:
 */
@Data
public class RoleVO {
    private Long id;

    @NotNull(message = "角色名不能为空")
    private String rolename;

    @NotNull(message = "角色名不能为空")
    private Integer status;

    @NotNull(message = "角色名不能为空")
    private String description;
}
