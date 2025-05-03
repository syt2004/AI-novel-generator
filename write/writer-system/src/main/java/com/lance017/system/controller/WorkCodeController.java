package com.lance017.system.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.RandomUtil;
import com.lance017.common.utils.DateUtils;
import com.lance017.common.utils.SecurityUtils;
import com.lance017.system.domain.excel.WorkCodeExcel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lance017.common.annotation.Log;
import com.lance017.common.core.controller.BaseController;
import com.lance017.common.core.domain.AjaxResult;
import com.lance017.common.enums.BusinessType;
import com.lance017.system.domain.WorkCode;
import com.lance017.system.service.IWorkCodeService;
import com.lance017.common.utils.poi.ExcelUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lance017.common.core.page.TableDataInfo;

/**
 * 兑换码Controller
 *
 * @author writer
 * @date 2024-10-05
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/wordCode")
public class WorkCodeController extends BaseController {

    private final IWorkCodeService workCodeService;

    /**
     * 查询兑换码列表
     */
    @PreAuthorize("@ss.hasPermi('system:wordCode:list')")
    @GetMapping("/list")
    public TableDataInfo list(WorkCode workCode) {
        startPage();
        List<WorkCode> list = workCodeService.selectWorkCodeList(workCode);
        return getDataTable(list);
    }

    /**
     * 导出兑换码列表
     */
    @PreAuthorize("@ss.hasPermi('system:wordCode:export')")
    @Log(title = "兑换码", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WorkCode workCode) {
        List<WorkCode> list = workCodeService.selectWorkCodeList(workCode);
        ExcelUtil<WorkCode> util = new ExcelUtil<WorkCode>(WorkCode.class);
        util.exportExcel(response, list, "兑换码数据");
    }

    /**
     * 获取兑换码详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:wordCode:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(workCodeService.selectWorkCodeById(id));
    }

    /**
     * 新增兑换码
     */
    @PreAuthorize("@ss.hasPermi('system:wordCode:add')")
    @Log(title = "兑换码", businessType = BusinessType.INSERT)
    @PostMapping
    public void add(HttpServletResponse response, WorkCode workCode) {
        List<WorkCodeExcel> workCodes = workCodeService.createWorkCode(workCode);
        ExcelUtil<WorkCodeExcel> util = new ExcelUtil<WorkCodeExcel>(WorkCodeExcel.class);
        util.exportExcel(response, workCodes, "兑换码");
    }

    /**
     * 修改兑换码
     */
    @PreAuthorize("@ss.hasPermi('system:wordCode:edit')")
    @Log(title = "兑换码", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WorkCode workCode) {
        return toAjax(workCodeService.updateWorkCode(workCode));
    }

    /**
     * 删除兑换码
     */
    @PreAuthorize("@ss.hasPermi('system:wordCode:remove')")
    @Log(title = "兑换码", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(workCodeService.deleteWorkCodeByIds(ids));
    }
}
