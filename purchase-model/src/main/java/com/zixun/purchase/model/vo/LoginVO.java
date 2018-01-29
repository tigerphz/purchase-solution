package com.zixun.purchase.model.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-21 下午10:06
 * @version: V1.0
 * @modified by:
 */
@Data
public class LoginVO {
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}
