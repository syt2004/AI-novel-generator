package com.lance017.workbench.controller;

import com.lance017.common.core.domain.AjaxResult;
import com.lance017.workbench.annotation.WorkbenchSign;
import com.lance017.workbench.domain.request.*;
import com.lance017.workbench.service.ApiBookService;
import com.lance017.workbench.service.ApiPartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/part")
@AllArgsConstructor
public class ApiPartController {

    private final ApiPartService apiPartService;

    /**
     * 创建作品
     * @param addBook
     * @return
     */
    @WorkbenchSign
    @RequestMapping("/add")
    public AjaxResult add(@RequestBody @Valid PartAddRequest addBook) {
        log.info("PartAddRequest:{}", addBook);
        return AjaxResult.success("创建成功", apiPartService.add(addBook));
    }


    /**
     * 获取作品详情
     * @return
     */
    @WorkbenchSign
    @RequestMapping("/info")
    public AjaxResult info(@RequestBody @Valid PartInfoRequest request) {
        log.info("PartInfoRequest:{}", request);
        return AjaxResult.success(apiPartService.info(request));
    }

    @WorkbenchSign
    @RequestMapping("/sort")
    public AjaxResult sort(@RequestBody @Valid PartSortRequest request) {
        log.info("PartSortRequest:{}", request);
        apiPartService.sort(request);
        return AjaxResult.success();
    }

    /**
     * 修改作品信息
     * @param request
     * @return
     */
    @WorkbenchSign
    @RequestMapping("/update")
    public AjaxResult update(@RequestBody @Valid PartUpdateRequest request) {
        log.info("PartUpdateRequest:{}", request);
        return AjaxResult.success("修改成功", apiPartService.update(request));
    }


    @WorkbenchSign
    @RequestMapping("/delete")
    public AjaxResult delete(@RequestBody @Valid PartDeleteRequest request) {
        log.info("PartDeleteRequest:{}", request);
        apiPartService.delete(request);
        return AjaxResult.success("删除成功");
    }


    /**
     * 获取作品列表
     * @return
     */
    @WorkbenchSign
    @RequestMapping("/list")
    public AjaxResult list(@RequestBody @Valid PartListRequest request) {
        log.info("PartListRequest:{}", request);
        return AjaxResult.success(apiPartService.list(request));
    }

}
