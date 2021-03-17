package com.mohe.blog.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//web拦截器的配置类，例如告诉登录拦截器要拦截什么
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加登录拦截器，并且给拦截器绑定路径(admin下面的所有路径，但是排除掉登录页面和登录页面的提交路径)
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**").excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");
    }

    //放行静态资源
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**");
    }
}
