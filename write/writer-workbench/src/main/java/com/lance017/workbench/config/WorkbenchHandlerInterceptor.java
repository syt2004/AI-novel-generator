package com.lance017.workbench.config;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.lance017.common.core.domain.AjaxResult;
import com.lance017.common.core.redis.RedisCache;
import com.lance017.common.enums.CustomUserStatus;
import com.lance017.common.exception.ServiceException;
import com.lance017.system.domain.WorkUser;
import com.lance017.system.service.IWorkUserService;
import com.lance017.workbench.annotation.WorkbenchSign;
import com.lance017.workbench.constant.WorkBenchConstant;
import com.lance017.workbench.context.WorkbenchContextHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
@Component
public class WorkbenchHandlerInterceptor implements HandlerInterceptor {


    public final RedisCache redisCache;

    private final IWorkUserService workUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 判断是否需要校验请求token许可，只需要看目标请求处理方法上是否有自定义请求token许可注解-TokenPermission
        if (this.checkTargetMethodHasTokenPermission(handler)){
            // 需要进行请求token许可校验，从请求头中获取token参数，做token鉴权业务逻辑处理
            String tokenStr = request.getHeader(WorkBenchConstant.WORKBENCH_TOKEN_KEY);

            // 判断token是否合法，如果没有，直接鉴权失败，跳转到登录
            if(StrUtil.isBlank(tokenStr)){
                // token参数为空，返回鉴权失败
                this.returnTokenCheckJson(response, 401, "用户登陆过期,请重新登录");
                // 权限校验失败，需要拦截请求
                return false;
            }

            String token = tokenStr.replace("Bearer ", "");

            Object cacheObject = redisCache.getCacheObject(WorkBenchConstant.WORKBENCH_USER_KEY + token);

            if(ObjectUtils.isEmpty(cacheObject)) {
                this.returnTokenCheckJson(response, 401, "用户登陆过期,请重新登录");
                // 权限校验失败，需要拦截请求
                return false;
            }

            WorkUser workUser = workUserService.selectWorkUserById((Long) cacheObject);

            if(ObjectUtils.isEmpty(workUser)){
                throw new ServiceException("用户登陆过期,请重新登录", 401);
            }

            if (ObjectUtils.notEqual(workUser.getStatus(), CustomUserStatus.NORMAL.getCode())) {
                throw new ServiceException("用户状态异常");
            }

            WorkbenchContextHolder.getContext().setWorkbenchUser(workUser);
        }

        // 不需要拦截，直接放行
        return true;
    }


    public void returnTokenCheckJson(HttpServletResponse response, Integer returnCode, String returnMsg){
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            response.getWriter().print(JSONUtil.toJsonStr(AjaxResult.error(returnCode, returnMsg)));
        } catch (IOException e) {
            log.warn("****** 请求token许可拦截器返回结果异常：{} ******", e.getMessage());
        }
    }


    public boolean checkTargetMethodHasTokenPermission(Object handler){

        // 判断当前处理的handler是否已经映射到目标请求处理方法，看是不是HandlerMethod的实例对象
        if(handler instanceof HandlerMethod){
            // 强转为目标请求处理方法的实例对象，因为：HandlerMethod对象封装了目标请求处理方法的所有内容，包括方法所有的声明
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            // 尝试获取目标请求处理方法上，是否添加了自定义请求token许可注解-TokenPermission，取到了就是加了，取不到就没加
            WorkbenchSign workbenchSign = handlerMethod.getMethod().getAnnotation(WorkbenchSign.class);

            // 判断是否成功获取到请求token许可注解，如果没有获取到，不一定代表不需要进行权限校验，因为此注解还可能加载处理类，要再次尝试从请求处理方法所在处理类上获取该注解
            if(ObjectUtil.isEmpty(workbenchSign)){
                workbenchSign = handlerMethod.getMethod().getDeclaringClass().getAnnotation(WorkbenchSign.class);
            }

            // 最终判断是否需要进行请求token许可校验，如果获取到了，说明需要校验，否则直接放行
            return null != workbenchSign;
        }

        // 请求不是需要进行鉴权操作，直接返回false
        return false;
    }


}
