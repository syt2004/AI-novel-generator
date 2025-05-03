package com.lance017.workbench.service;

import com.lance017.workbench.domain.request.LoginRequest;
import com.lance017.workbench.domain.request.RegisterRequest;
import com.lance017.workbench.domain.request.UserCodeRequest;
import com.lance017.workbench.domain.request.UserInfoUpdateRequest;

public interface ApiLoginService {

    /**
     * 注册
     * @param register 注册信息
     */
    void register(RegisterRequest register);

    /**
     * 登录
     * @param login 登录信息
     * @return token
     */
    Object login(LoginRequest login);

    /**
     * 修改用户信息
     * @param request
     */
    void userInfoUpdate(UserInfoUpdateRequest request);

    /**
     * 获取用户信息
     * @return
     */
    Object userinfo();


    void subCode(Long workUserId, Long words);



    Object usercode(UserCodeRequest request);

}
