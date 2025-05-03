package com.lance017.workbench.domain.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserInfoUpdateRequest {
    /**
     * 用户名
     */
    @NotBlank(message = "昵称不能为空")
    private String nickname;

}
