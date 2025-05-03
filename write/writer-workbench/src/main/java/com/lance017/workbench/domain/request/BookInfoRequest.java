package com.lance017.workbench.domain.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BookInfoRequest {

    @NotBlank(message = "参数错误")
    private String id;



}
