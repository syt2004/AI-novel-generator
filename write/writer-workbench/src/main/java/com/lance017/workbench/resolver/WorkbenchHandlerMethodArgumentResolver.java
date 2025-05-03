package com.lance017.workbench.resolver;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 有@LoginUser注解的方法参数，注入当前登录用户
 *
 */
@Slf4j
//@Component
@AllArgsConstructor
public class WorkbenchHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {



    private final RedisCache redisCache;

    private final IWorkUserService workUserService;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(WorkbenchSign.class) && parameter.hasParameterAnnotation(WorkbenchSign.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) throws Exception {

        Object object = request.getHeader("workbench-token");

        if(ObjectUtils.isEmpty(object)){
            throw new ServiceException("用户登陆过期,请重新登录", 401);
        }

        String token = object.toString().replace("Bearer ", "");

        Object cacheObject = redisCache.getCacheObject(WorkBenchConstant.WORKBENCH_USER_KEY + token);

        if(ObjectUtils.isEmpty(cacheObject)) {
            throw new ServiceException("用户登陆过期,请重新登录", 401);
        }

        WorkUser workUser = workUserService.selectWorkUserById((Long) cacheObject);

        if(ObjectUtils.isEmpty(workUser)){
            throw new ServiceException("用户登陆过期,请重新登录", 401);
        }

        if (ObjectUtils.notEqual(workUser.getStatus(), CustomUserStatus.NORMAL.getCode())) {
            throw new ServiceException("用户状态异常");
        }

        WorkbenchContextHolder.getContext().setWorkbenchUser(workUser);

        return workUser;
    }
}
