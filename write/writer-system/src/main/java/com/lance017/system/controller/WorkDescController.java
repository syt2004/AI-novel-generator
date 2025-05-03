package com.lance017.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.lance017.system.domain.WorkDesc;
import com.lance017.system.service.IWorkDescService;
import com.lance017.common.utils.poi.ExcelUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lance017.common.core.page.TableDataInfo;

/**
 * 提示词Controller
 *
 * @author writer
 * @date 2024-10-07
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/desc")
public class WorkDescController extends BaseController {

    private final IWorkDescService workDescService;

    /**
     * 查询提示词列表
     */
    @PreAuthorize("@ss.hasPermi('system:desc:list')")
    @GetMapping("/list")
    public TableDataInfo list(WorkDesc workDesc) {
        startPage();
        List<WorkDesc> list = workDescService.selectWorkDescList(workDesc);
        return getDataTable(list);
    }

    /**
     * 导出提示词列表
     */
    @PreAuthorize("@ss.hasPermi('system:desc:export')")
    @Log(title = "提示词", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WorkDesc workDesc) {
        List<WorkDesc> list = workDescService.selectWorkDescList(workDesc);
        ExcelUtil<WorkDesc> util = new ExcelUtil<WorkDesc>(WorkDesc.class);
        util.exportExcel(response, list, "提示词数据");
    }

    /**
     * 获取提示词详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:desc:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(workDescService.selectWorkDescById(id));
    }

    /**
     * 新增提示词
     */
    @PreAuthorize("@ss.hasPermi('system:desc:add')")
    @Log(title = "提示词", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WorkDesc workDesc) {
        return toAjax(workDescService.insertWorkDesc(workDesc));
    }

    /**
     * 修改提示词
     */
    @PreAuthorize("@ss.hasPermi('system:desc:edit')")
    @Log(title = "提示词", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WorkDesc workDesc) {
        return toAjax(workDescService.updateWorkDesc(workDesc));
    }

    /**
     * 删除提示词
     */
    @PreAuthorize("@ss.hasPermi('system:desc:remove')")
    @Log(title = "提示词", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(workDescService.deleteWorkDescByIds(ids));
    }
}
