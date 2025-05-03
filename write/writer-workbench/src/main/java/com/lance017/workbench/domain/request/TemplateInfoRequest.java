package com.lance017.workbench.domain.request;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TemplateInfoRequest {

    @NotNull(message = "参数错误")
    private Long bookId;

}
