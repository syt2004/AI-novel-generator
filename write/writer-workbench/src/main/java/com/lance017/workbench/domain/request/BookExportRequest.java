package com.lance017.workbench.domain.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BookExportRequest {

    @NotBlank(message = "参数错误")
    private String id;



}
