package com.lance017.workbench.domain.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RoleAddRequest {



    @NotBlank(message = "角色姓名不能为空")
    private String name;

    @NotNull(message = "性别参数错误")
    private Integer sex;

    /** 角色性格 */
    @NotBlank(message = "角色性格不能为空")
    private String characte;

    /** 角色信息 */
    @NotBlank(message = "角色信息不能为空")
    private String plot;

    @NotNull(message = "作品参数错误")
    private Long bookId;

    @NotNull(message = "显示参数错误")
    private Integer isShow;

}
