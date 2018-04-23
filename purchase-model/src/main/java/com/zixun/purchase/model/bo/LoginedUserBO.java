package com.zixun.purchase.model.bo;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-21 下午10:45
 * @version: V1.0
 * @modified by:
 */
@Data
public class LoginedUserBO {
    private Long id;

    private String username;

    private String nickname;

    private List<String> roles;

    private List<String> permissions;
}
