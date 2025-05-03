package com.lance017.workbench.config;


import com.lance017.workbench.resolver.WorkbenchHandlerMethodArgumentResolver;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * MVC配置
 *
 */
@Configuration
@AllArgsConstructor
public class WorkBenchWebMvcConfig implements WebMvcConfigurer {


//    private final WorkbenchHandlerMethodArgumentResolver resolver;

    private final WorkbenchHandlerInterceptor interceptor;


//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        argumentResolvers.add(resolver);
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/api/**");
    }
}
