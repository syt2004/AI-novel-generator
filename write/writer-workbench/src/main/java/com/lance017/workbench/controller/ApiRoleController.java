package com.lance017.workbench.controller;

import com.lance017.common.core.domain.AjaxResult;
import com.lance017.workbench.annotation.WorkbenchSign;
import com.lance017.workbench.domain.request.*;
import com.lance017.workbench.service.ApiBookService;
import com.lance017.workbench.service.ApiRoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/role")
@AllArgsConstructor
public class ApiRoleController {




    private final ApiRoleService apiRoleService;

    /**
     * 创建角色
     * @return
     */
    @WorkbenchSign
    @RequestMapping("/add")
    public AjaxResult add(@RequestBody @Valid RoleAddRequest request) {
        log.info("RoleAddRequest:{}", request);
        return AjaxResult.success("创建成功", apiRoleService.add(request));
    }


//    /**
//     * 获取作品详情
//     * @return
//     */
//    @WorkbenchSign
//    @RequestMapping("/info")
//    public AjaxResult info(@RequestBody @Valid BookInfoRequest request) {
//        log.info("infoBook:{}", request);
//        return AjaxResult.success(apiBookService.infoBook(request));
//    }

    /**
     * 修改角色
     * @return
     */
    @WorkbenchSign
    @RequestMapping("/update")
    public AjaxResult update(@RequestBody @Valid RoleUpdateRequest request) {
        log.info("RoleUpdateRequest:{}", request);
        apiRoleService.update(request);
        return AjaxResult.success("修改成功");
    }

    @WorkbenchSign
    @RequestMapping("/delete")
    public AjaxResult delete(@RequestBody @Valid RoleDeleteRequest request) {
        log.info("RoleDeleteRequest:{}", request);
        apiRoleService.delete(request);
        return AjaxResult.success("删除成功");
    }



    @WorkbenchSign
    @RequestMapping("/list")
    public AjaxResult list(@RequestBody @Valid RoleListRequest request) {
        log.info("list:{}", request);
        return AjaxResult.success(apiRoleService.list(request));
    }


    @WorkbenchSign
    @RequestMapping("/listByAi")
    public AjaxResult listByAi(@RequestBody @Valid RoleListRequest request) {
        log.info("list:{}", request);
        return AjaxResult.success(apiRoleService.listByAi(request));
    }

    @WorkbenchSign
    @RequestMapping("/aiAdd")
    public AjaxResult aiAdd(@RequestBody @Valid RoleAIAddRequest request) {
        log.info("aiAdd:{}", request);
        int i = apiRoleService.aiAdd(request);
        return AjaxResult.success("识别" + i + "个角色成功");
    }

}
