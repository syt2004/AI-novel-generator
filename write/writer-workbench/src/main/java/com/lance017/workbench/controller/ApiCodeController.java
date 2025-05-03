package com.lance017.workbench.controller;

import com.lance017.common.core.domain.AjaxResult;
import com.lance017.workbench.annotation.WorkbenchSign;
import com.lance017.workbench.domain.request.ExchangeRequest;
import com.lance017.workbench.service.ApiCodeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/code")
@AllArgsConstructor
public class ApiCodeController {

    private final ApiCodeService apiCodeService;

    @WorkbenchSign
    @RequestMapping("/exchange")
    public AjaxResult exchange(@RequestBody @Valid ExchangeRequest exchange) {
        log.info("exchange:{}", exchange);
        apiCodeService.exchange(exchange);
        return AjaxResult.success("兑换成功");
    }

}
