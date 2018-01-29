package com.zixun.purchase.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-28 下午7:33
 * @version: V1.0
 * @modified by:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRolesVO {
    @NonNull
    private Long userId;

    private List<Long> roleIds;
}
