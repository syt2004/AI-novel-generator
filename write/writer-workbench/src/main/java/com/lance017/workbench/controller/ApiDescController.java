package com.lance017.workbench.controller;

import com.lance017.common.core.domain.AjaxResult;
import com.lance017.workbench.annotation.WorkbenchSign;
import com.lance017.workbench.domain.request.MyDescListRequest;
import com.lance017.workbench.domain.request.TemplateInfoRequest;
import com.lance017.workbench.domain.request.TemplateUpdateRequest;
import com.lance017.workbench.service.ApiDescService;
import com.lance017.workbench.service.ApiTemplateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/desc")
@AllArgsConstructor
public class ApiDescController {

    private final ApiDescService apiDescService;


    @WorkbenchSign
    @RequestMapping("/my/list")
    public AjaxResult list(@RequestBody @Valid MyDescListRequest request) {
        log.info("MyDescListRequest:{}", request);
        return AjaxResult.success(apiDescService.list(request));
    }






}
