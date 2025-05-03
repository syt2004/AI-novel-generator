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
import com.lance017.system.domain.WorkUserCode;
import com.lance017.system.service.IWorkUserCodeService;
import com.lance017.common.utils.poi.ExcelUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lance017.common.core.page.TableDataInfo;

/**
 * 用户兑换码Controller
 *
 * @author writer
 * @date 2024-10-05
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/code")
public class WorkUserCodeController extends BaseController {

    private final IWorkUserCodeService workUserCodeService;

    /**
     * 查询用户兑换码列表
     */
    @PreAuthorize("@ss.hasPermi('system:code:list')")
    @GetMapping("/list")
    public TableDataInfo list(WorkUserCode workUserCode) {
        startPage();
        List<WorkUserCode> list = workUserCodeService.selectWorkUserCodeList(workUserCode);
        return getDataTable(list);
    }

    /**
     * 导出用户兑换码列表
     */
    @PreAuthorize("@ss.hasPermi('system:code:export')")
    @Log(title = "用户兑换码", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WorkUserCode workUserCode) {
        List<WorkUserCode> list = workUserCodeService.selectWorkUserCodeList(workUserCode);
        ExcelUtil<WorkUserCode> util = new ExcelUtil<WorkUserCode>(WorkUserCode.class);
        util.exportExcel(response, list, "用户兑换码数据");
    }

    /**
     * 获取用户兑换码详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:code:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(workUserCodeService.selectWorkUserCodeById(id));
    }

    /**
     * 新增用户兑换码
     */
    @PreAuthorize("@ss.hasPermi('system:code:add')")
    @Log(title = "用户兑换码", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WorkUserCode workUserCode) {
        return toAjax(workUserCodeService.insertWorkUserCode(workUserCode));
    }

    /**
     * 修改用户兑换码
     */
    @PreAuthorize("@ss.hasPermi('system:code:edit')")
    @Log(title = "用户兑换码", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WorkUserCode workUserCode) {
        return toAjax(workUserCodeService.updateWorkUserCode(workUserCode));
    }

    /**
     * 删除用户兑换码
     */
    @PreAuthorize("@ss.hasPermi('system:code:remove')")
    @Log(title = "用户兑换码", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(workUserCodeService.deleteWorkUserCodeByIds(ids));
    }
}
