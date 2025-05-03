package com.lance017.workbench.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import com.lance017.common.core.domain.AjaxResult;
import com.lance017.common.utils.poi.ExcelUtil;
import com.lance017.system.domain.WorkUserWrite;
import com.lance017.workbench.annotation.WorkbenchSign;
import com.lance017.workbench.domain.request.*;
import com.lance017.workbench.service.ApiBookService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/book")
@AllArgsConstructor
public class ApiBookController {

    private final ApiBookService apiBookService;

    /**
     * 创建作品
     * @param addBook
     * @return
     */
    @WorkbenchSign
    @RequestMapping("/add")
    public AjaxResult add(@RequestBody @Valid BookAddRequest addBook) {
        log.info("addBook:{}", addBook);
        apiBookService.addBook(addBook);
        return AjaxResult.success("创建成功");
    }


    /**
     * 获取作品详情
     * @return
     */
    @WorkbenchSign
    @RequestMapping("/info")
    public AjaxResult info(@RequestBody @Valid BookInfoRequest request) {
        log.info("infoBook:{}", request);
        return AjaxResult.success(apiBookService.infoBook(request));
    }

    /**
     * 修改作品信息
     * @param request
     * @return
     */
    @WorkbenchSign
    @RequestMapping("/update")
    public AjaxResult update(@RequestBody @Valid BookUpdateRequest request) {
        log.info("updateBook:{}", request);
        apiBookService.update(request);
        return AjaxResult.success("修改成功");
    }


    @WorkbenchSign
    @RequestMapping("/delete")
    public AjaxResult delete(@RequestBody @Valid BookDeleteRequest request) {
        log.info("BookDeleteRequest:{}", request);
        apiBookService.delete(request);
        return AjaxResult.success("删除成功");
    }


    /**
     * 获取作品列表
     * @return
     */
    @WorkbenchSign
    @RequestMapping("/list")
    public AjaxResult list() {
        log.info("list");
        return AjaxResult.success(apiBookService.list());
    }


    @SneakyThrows
    @WorkbenchSign
    @RequestMapping("/export")
    public void export(HttpServletResponse response, @RequestBody @Valid BookExportRequest request) {
        Object export = apiBookService.export(request);
        ServletOutputStream outputStream = response.getOutputStream();
        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(DateUtil.today() + ".txt", "utf-8"));
        outputStream.write(export.toString().getBytes());
        IoUtil.write(outputStream, "utf-8",  true, export);
    }

}
