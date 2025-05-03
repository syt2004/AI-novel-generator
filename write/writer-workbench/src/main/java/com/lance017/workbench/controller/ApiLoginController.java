package com.lance017.workbench.controller;


import com.lance017.common.core.domain.AjaxResult;
import com.lance017.workbench.annotation.WorkbenchSign;
import com.lance017.workbench.domain.request.LoginRequest;
import com.lance017.workbench.domain.request.RegisterRequest;
import com.lance017.workbench.domain.request.UserCodeRequest;
import com.lance017.workbench.domain.request.UserInfoUpdateRequest;
import com.lance017.workbench.service.ApiLoginService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 工作台登录
 */
@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ApiLoginController {

    private final ApiLoginService apiLoginService;

    /**
     * 注册
     * @param register 注册信息
     * @return 注册结果
     */
    @RequestMapping("/register")
    public AjaxResult register(@RequestBody @Valid RegisterRequest register) {
        log.info("register:{}", register);
        apiLoginService.register(register);
        return AjaxResult.success(register);
    }


    /**
     * 处理登录请求
     * @param login 登录请求对象，包含用户登录所需的信息
     * @return 登录结果
     */
    @RequestMapping("/login")
    public AjaxResult login(@RequestBody @Valid LoginRequest login) {
        log.info("login:{}", login);
        return AjaxResult.success(apiLoginService.login(login));
    }

    @WorkbenchSign
    @RequestMapping("/userinfo/update")
    public AjaxResult userInfoUpdate(@RequestBody @Valid UserInfoUpdateRequest request) {
        log.info("userInfoUpdate:{}", request);
        apiLoginService.userInfoUpdate(request);
        return AjaxResult.success("修改成功");
    }

    /**
     * 获取用户信息
     * @return 用户信息
     */
    @WorkbenchSign
    @RequestMapping("/userinfo")
    public AjaxResult userinfo() {
        log.info("userinfo");
        return AjaxResult.success(apiLoginService.userinfo());
    }


    @WorkbenchSign
    @RequestMapping("/usercode")
    public AjaxResult usercode(@RequestBody UserCodeRequest request) {
        log.info("usercode");
        return AjaxResult.success(apiLoginService.usercode(request));
    }

}
