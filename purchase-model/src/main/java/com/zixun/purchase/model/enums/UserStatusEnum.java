package com.zixun.purchase.model.enums;

import lombok.Getter;

/**
 * 用户状态
 */
public enum UserStatusEnum {
    /**
     * 删除
     */
    DELETE("删除", -1),
    /**
     * 正常
     */
    NORMAL("正常", 0),
    /**
     * 锁定
     */
    LOCKED("锁定", 1);

    @Getter
    private String name;

    @Getter
    private Integer code;

    UserStatusEnum(String name, Integer code) {
        this.name = name;
        this.code = code;
    }
}
