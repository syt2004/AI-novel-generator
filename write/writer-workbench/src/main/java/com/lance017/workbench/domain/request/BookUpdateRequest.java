package com.lance017.workbench.domain.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BookUpdateRequest {

    @NotBlank(message = "参数错误")
    private String id;
    @NotBlank(message = "书名不能为空")
    private String title;

    private String content;

}
