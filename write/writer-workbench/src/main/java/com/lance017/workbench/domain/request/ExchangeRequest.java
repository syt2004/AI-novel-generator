package com.lance017.workbench.domain.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ExchangeRequest {

    @NotBlank(message = "兑换码不能为空")
    private String code;

}
