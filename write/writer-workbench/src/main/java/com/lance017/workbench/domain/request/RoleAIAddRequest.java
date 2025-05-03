package com.lance017.workbench.domain.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RoleAIAddRequest {



    @NotBlank(message = "数据不能为空")
    private String info;

    @NotNull(message = "作品参数错误")
    private Long bookId;

}
