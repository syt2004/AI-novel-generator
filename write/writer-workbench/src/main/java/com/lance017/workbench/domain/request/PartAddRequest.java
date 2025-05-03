package com.lance017.workbench.domain.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PartAddRequest {

    @NotBlank(message = "章节名不能为空")
    private String partTitle;

    private String content;

    @NotNull(message = "作品参数错误")
    private Long bookId;

}
