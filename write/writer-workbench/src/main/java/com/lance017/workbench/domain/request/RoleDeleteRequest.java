package com.lance017.workbench.domain.request;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RoleDeleteRequest {

    @NotNull(message = "参数错误")
    private Long id;

    @NotNull(message = "作品参数错误")
    private Long bookId;

}
