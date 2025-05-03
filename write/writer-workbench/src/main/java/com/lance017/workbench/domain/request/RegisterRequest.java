package com.lance017.workbench.domain.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegisterRequest {
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Size(min = 8, max = 16, message = "用户名长度为8-16位")
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 8, max = 16, message = "密码长度为8-16位")
    private String password;
    /**
     * 昵称
     */
    private String inviteCode;

}
