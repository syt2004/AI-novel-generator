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
import com.lance017.system.domain.WorkUserWrite;
import com.lance017.system.service.IWorkUserWriteService;
import com.lance017.common.utils.poi.ExcelUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lance017.common.core.page.TableDataInfo;

/**
 * 用户生成Controller
 *
 * @author writer
 * @date 2024-10-09
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/write")
public class WorkUserWriteController extends BaseController {

    private final IWorkUserWriteService workUserWriteService;

    /**
     * 查询用户生成列表
     */
    @PreAuthorize("@ss.hasPermi('system:write:list')")
    @GetMapping("/list")
    public TableDataInfo list(WorkUserWrite workUserWrite) {
        startPage();
        List<WorkUserWrite> list = workUserWriteService.selectWorkUserWriteList(workUserWrite);
        return getDataTable(list);
    }

    /**
     * 导出用户生成列表
     */
    @PreAuthorize("@ss.hasPermi('system:write:export')")
    @Log(title = "用户生成", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WorkUserWrite workUserWrite) {
        List<WorkUserWrite> list = workUserWriteService.selectWorkUserWriteList(workUserWrite);
        ExcelUtil<WorkUserWrite> util = new ExcelUtil<WorkUserWrite>(WorkUserWrite.class);
        util.exportExcel(response, list, "用户生成数据");
    }

    /**
     * 获取用户生成详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:write:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(workUserWriteService.selectWorkUserWriteById(id));
    }

    /**
     * 新增用户生成
     */
    @PreAuthorize("@ss.hasPermi('system:write:add')")
    @Log(title = "用户生成", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WorkUserWrite workUserWrite) {
        return toAjax(workUserWriteService.insertWorkUserWrite(workUserWrite));
    }

    /**
     * 修改用户生成
     */
    @PreAuthorize("@ss.hasPermi('system:write:edit')")
    @Log(title = "用户生成", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WorkUserWrite workUserWrite) {
        return toAjax(workUserWriteService.updateWorkUserWrite(workUserWrite));
    }

    /**
     * 删除用户生成
     */
    @PreAuthorize("@ss.hasPermi('system:write:remove')")
    @Log(title = "用户生成", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(workUserWriteService.deleteWorkUserWriteByIds(ids));
    }
}
