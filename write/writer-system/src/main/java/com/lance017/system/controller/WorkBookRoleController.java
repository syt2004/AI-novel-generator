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
import com.lance017.system.domain.WorkBookRole;
import com.lance017.system.service.IWorkBookRoleService;
import com.lance017.common.utils.poi.ExcelUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lance017.common.core.page.TableDataInfo;

/**
 * 小说角色Controller
 *
 * @author writer
 * @date 2024-10-05
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/bookRole")
public class WorkBookRoleController extends BaseController {

    private final IWorkBookRoleService workBookRoleService;

    /**
     * 查询小说角色列表
     */
    @PreAuthorize("@ss.hasPermi('system:bookRole:list')")
    @GetMapping("/list")
    public TableDataInfo list(WorkBookRole workBookRole) {
        startPage();
        List<WorkBookRole> list = workBookRoleService.selectWorkBookRoleList(workBookRole);
        return getDataTable(list);
    }

    /**
     * 导出小说角色列表
     */
    @PreAuthorize("@ss.hasPermi('system:bookRole:export')")
    @Log(title = "小说角色", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WorkBookRole workBookRole) {
        List<WorkBookRole> list = workBookRoleService.selectWorkBookRoleList(workBookRole);
        ExcelUtil<WorkBookRole> util = new ExcelUtil<WorkBookRole>(WorkBookRole.class);
        util.exportExcel(response, list, "小说角色数据");
    }

    /**
     * 获取小说角色详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:bookRole:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(workBookRoleService.selectWorkBookRoleById(id));
    }

    /**
     * 新增小说角色
     */
    @PreAuthorize("@ss.hasPermi('system:bookRole:add')")
    @Log(title = "小说角色", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WorkBookRole workBookRole) {
        return toAjax(workBookRoleService.insertWorkBookRole(workBookRole));
    }

    /**
     * 修改小说角色
     */
    @PreAuthorize("@ss.hasPermi('system:bookRole:edit')")
    @Log(title = "小说角色", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WorkBookRole workBookRole) {
        return toAjax(workBookRoleService.updateWorkBookRole(workBookRole));
    }

    /**
     * 删除小说角色
     */
    @PreAuthorize("@ss.hasPermi('system:bookRole:remove')")
    @Log(title = "小说角色", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(workBookRoleService.deleteWorkBookRoleByIds(ids));
    }
}
