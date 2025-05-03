package com.lance017.workbench.domain.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AIPolishRequest {

    @NotNull(message = "参数错误")
    private Long bookId;


    @NotNull(message = "参数错误")
    private Integer requiresType;

    @NotNull(message = "参数错误")
    private Long requiresId;
    /** 要求 */
    private String requires;

    @NotBlank(message = "扩写文本不能为空")
    private String content;

    private String roles;


    /** 角色关系 */
    private String relation;

}
