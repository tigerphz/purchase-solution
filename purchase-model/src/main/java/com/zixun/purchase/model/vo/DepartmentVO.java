package com.zixun.purchase.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-14 上午10:42
 * @version: V1.0
 * @modified by:
 */
@Data
public class DepartmentVO {
    private Long id;

    private String deptname;

    private Integer status;

    private String description;

    private Long parentid;

    private Date createdate;

    private String createusername;

    private Date modifydate;

    private String modifyusername;
}
