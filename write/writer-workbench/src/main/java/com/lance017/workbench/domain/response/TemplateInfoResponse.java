package com.lance017.workbench.domain.response;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TemplateInfoResponse {

    private Long bookId;

    private Long id;

    private String bg;

    /** 角色关系 */
    private String relation;

    /** 剧情id */
    private Long plotId;

    private String plot;

    private Integer styleType;

    /** 风格id */
    private Long styleId;

    /** 风格 */
    private String style;

    private Integer requiresType;

    /** 要求id */
    private Long requiresId;

    /** 要求 */
    private String requires;

    private Date createTime;

    private String createBy;

    private String updateBy;

    private Date updateTime;

    private String roles;

}
