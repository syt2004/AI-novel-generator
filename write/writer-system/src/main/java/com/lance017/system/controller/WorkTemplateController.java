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
import com.lance017.system.domain.WorkTemplate;
import com.lance017.system.service.IWorkTemplateService;
import com.lance017.common.utils.poi.ExcelUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lance017.common.core.page.TableDataInfo;

/**
 * 小说模板Controller
 *
 * @author writer
 * @date 2024-10-07
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/template")
public class WorkTemplateController extends BaseController {

    private final IWorkTemplateService workTemplateService;

    /**
     * 查询小说模板列表
     */
    @PreAuthorize("@ss.hasPermi('system:template:list')")
    @GetMapping("/list")
    public TableDataInfo list(WorkTemplate workTemplate) {
        startPage();
        List<WorkTemplate> list = workTemplateService.selectWorkTemplateList(workTemplate);
        return getDataTable(list);
    }

    /**
     * 导出小说模板列表
     */
    @PreAuthorize("@ss.hasPermi('system:template:export')")
    @Log(title = "小说模板", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WorkTemplate workTemplate) {
        List<WorkTemplate> list = workTemplateService.selectWorkTemplateList(workTemplate);
        ExcelUtil<WorkTemplate> util = new ExcelUtil<WorkTemplate>(WorkTemplate.class);
        util.exportExcel(response, list, "小说模板数据");
    }

    /**
     * 获取小说模板详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:template:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(workTemplateService.selectWorkTemplateById(id));
    }

    /**
     * 新增小说模板
     */
    @PreAuthorize("@ss.hasPermi('system:template:add')")
    @Log(title = "小说模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WorkTemplate workTemplate) {
        return toAjax(workTemplateService.insertWorkTemplate(workTemplate));
    }

    /**
     * 修改小说模板
     */
    @PreAuthorize("@ss.hasPermi('system:template:edit')")
    @Log(title = "小说模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WorkTemplate workTemplate) {
        return toAjax(workTemplateService.updateWorkTemplate(workTemplate));
    }

    /**
     * 删除小说模板
     */
    @PreAuthorize("@ss.hasPermi('system:template:remove')")
    @Log(title = "小说模板", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(workTemplateService.deleteWorkTemplateByIds(ids));
    }
}
