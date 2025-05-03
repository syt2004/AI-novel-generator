package com.lance017.workbench.domain.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PartUpdateRequest {

    @NotNull(message = "参数错误")
    private Long bookId;

    @NotNull(message = "参数错误")
    private Long id;

    @NotBlank(message = "章节名不能为空")
    private String partTitle;

    private String content;

}
