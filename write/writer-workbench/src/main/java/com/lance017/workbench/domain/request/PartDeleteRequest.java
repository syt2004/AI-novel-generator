package com.lance017.workbench.domain.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PartDeleteRequest {

    @NotBlank(message = "参数错误")
    private String id;

    @NotNull(message = "作品参数错误")
    private Long bookId;


}
