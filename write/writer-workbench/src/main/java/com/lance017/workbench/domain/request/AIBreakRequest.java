package com.lance017.workbench.domain.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AIBreakRequest {

    @NotNull(message = "参数错误")
    private Long bookId;

    @NotNull(message = "请选择章节")
    private List<Long> ids;

    @NotNull(message = "参数错误")
    private Integer requiresType;

    @NotNull(message = "参数错误")
    private Long requiresId;
    /** 要求 */
    private String requires;

    @NotNull(message = "参数错误")
    private Integer uuType;

}
