package com.lance017.workbench.domain.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TemplateUpdateRequest {

    @NotNull(message = "参数错误")
    private Long bookId;

    @NotBlank(message = "背景不能为空")
    private String bg;

    /** 角色关系 */
    private String relation;

    /** 剧情id */
//    @NotNull(message = "剧情id不能为空")
    private Long plotId;

    /** 剧情 */
    @NotBlank(message = "剧情不能为空")
    private String plot;

    @NotNull(message = "参数错误")
    private Integer styleType;

    /** 风格id */
    private Long styleId;

    /** 风格 */
    private String style;

    @NotNull(message = "参数错误")
    private Integer requiresType;

    /** 要求id */
    private Long requiresId;

    /** 要求 */
    private String requires;

    private String roles;


}
