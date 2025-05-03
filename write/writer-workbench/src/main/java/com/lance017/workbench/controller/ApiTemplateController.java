package com.lance017.workbench.controller;

import com.lance017.common.core.domain.AjaxResult;
import com.lance017.workbench.annotation.WorkbenchSign;
import com.lance017.workbench.domain.request.*;
import com.lance017.workbench.service.ApiTemplateService;
import com.lance017.workbench.service.ApiTemplateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/template")
@AllArgsConstructor
public class ApiTemplateController {

    private final ApiTemplateService apiTemplateService;





    @WorkbenchSign
    @RequestMapping("/info")
    public AjaxResult info(@RequestBody @Valid TemplateInfoRequest request) {
        log.info("TemplateInfoRequest:{}", request);
        return AjaxResult.success(apiTemplateService.info(request));
    }


    @WorkbenchSign
    @RequestMapping("/update")
    public AjaxResult update(@RequestBody @Valid TemplateUpdateRequest request) {
        log.info("TemplateUpdateRequest:{}", request);
        return AjaxResult.success("修改成功",apiTemplateService.update(request));
    }





}
