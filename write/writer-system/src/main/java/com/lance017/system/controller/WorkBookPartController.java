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
import com.lance017.system.domain.WorkBookPart;
import com.lance017.system.service.IWorkBookPartService;
import com.lance017.common.utils.poi.ExcelUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lance017.common.core.page.TableDataInfo;

/**
 * 小说章节Controller
 *
 * @author writer
 * @date 2024-10-05
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/bookPart")
public class WorkBookPartController extends BaseController {

    private final IWorkBookPartService workBookPartService;

    /**
     * 查询小说章节列表
     */
    @PreAuthorize("@ss.hasPermi('system:bookPart:list')")
    @GetMapping("/list")
    public TableDataInfo list(WorkBookPart workBookPart) {
        startPage();
        List<WorkBookPart> list = workBookPartService.selectWorkBookPartList(workBookPart);
        return getDataTable(list);
    }

    /**
     * 导出小说章节列表
     */
    @PreAuthorize("@ss.hasPermi('system:bookPart:export')")
    @Log(title = "小说章节", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WorkBookPart workBookPart) {
        List<WorkBookPart> list = workBookPartService.selectWorkBookPartList(workBookPart);
        ExcelUtil<WorkBookPart> util = new ExcelUtil<WorkBookPart>(WorkBookPart.class);
        util.exportExcel(response, list, "小说章节数据");
    }

    /**
     * 获取小说章节详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:bookPart:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(workBookPartService.selectWorkBookPartById(id));
    }

    /**
     * 新增小说章节
     */
    @PreAuthorize("@ss.hasPermi('system:bookPart:add')")
    @Log(title = "小说章节", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WorkBookPart workBookPart) {
        return toAjax(workBookPartService.insertWorkBookPart(workBookPart));
    }

    /**
     * 修改小说章节
     */
    @PreAuthorize("@ss.hasPermi('system:bookPart:edit')")
    @Log(title = "小说章节", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WorkBookPart workBookPart) {
        return toAjax(workBookPartService.updateWorkBookPart(workBookPart));
    }

    /**
     * 删除小说章节
     */
    @PreAuthorize("@ss.hasPermi('system:bookPart:remove')")
    @Log(title = "小说章节", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(workBookPartService.deleteWorkBookPartByIds(ids));
    }
}
