package com.zixun.purchase.model.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-10 下午4:12
 * @version: V1.0
 * @modified by:
 */
@Data
public class UserVO {
    private Long id;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "昵称不能为空")
    private String nickname;

    private String password;

    private Integer gender;

    private Integer status;

    private String cellphone;

    private String identitycard;

    private String fax;

    @Email(message="邮箱格式错误")
    private String email;

    private String qq;

    private String address;

    private Date createdate;

    private String createusername;

    private Date modifydate;

    private String modifyusername;
}
