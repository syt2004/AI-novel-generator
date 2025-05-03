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
import com.lance017.system.domain.WorkUser;
import com.lance017.system.service.IWorkUserService;
import com.lance017.common.utils.poi.ExcelUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lance017.common.core.page.TableDataInfo;

/**
 * workUserController
 *
 * @author writer
 * @date 2024-09-28
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/workUser")
public class WorkUserController extends BaseController {

    private final IWorkUserService workUserService;

    /**
     * 查询workUser列表
     */
    @PreAuthorize("@ss.hasPermi('system:workUser:list')")
    @GetMapping("/list")
    public TableDataInfo list(WorkUser workUser) {
        startPage();
        List<WorkUser> list = workUserService.selectWorkUserList(workUser);
        return getDataTable(list);
    }

    /**
     * 导出workUser列表
     */
    @PreAuthorize("@ss.hasPermi('system:workUser:export')")
    @Log(title = "workUser", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WorkUser workUser) {
        List<WorkUser> list = workUserService.selectWorkUserList(workUser);
        ExcelUtil<WorkUser> util = new ExcelUtil<WorkUser>(WorkUser.class);
        util.exportExcel(response, list, "workUser数据");
    }

    /**
     * 获取workUser详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:workUser:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(workUserService.selectWorkUserById(id));
    }

    /**
     * 新增workUser
     */
    @PreAuthorize("@ss.hasPermi('system:workUser:add')")
    @Log(title = "workUser", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WorkUser workUser) {
        return toAjax(workUserService.insertWorkUser(workUser));
    }

    /**
     * 修改workUser
     */
    @PreAuthorize("@ss.hasPermi('system:workUser:edit')")
    @Log(title = "workUser", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WorkUser workUser) {
        return toAjax(workUserService.updateWorkUser(workUser));
    }

    /**
     * 删除workUser
     */
    @PreAuthorize("@ss.hasPermi('system:workUser:remove')")
    @Log(title = "workUser", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(workUserService.deleteWorkUserByIds(ids));
    }
}
