package com.nnu.takeout.config;

import com.nnu.takeout.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC 配置：注册登录校验拦截器，放行登录、验证码等公开接口。
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginCheckInterceptor loginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/employee/**", "/category/**", "/dish/**", "/setmeal/**",
                        "/user/**", "/address/**", "/cart/**")
                .excludePathPatterns(
                        "/employee/login",      // 员工登录
                        "/user/login",          // 用户登录
                        "/user/sendMsg",        // 获取验证码

                        // 拦截器同样会作用于静态资源处理器（ResourceHandler），
                        // 若不排除，未登录访问 /user/login.html、/user/index.html 等页面
                        // 会直接收到 {"code":0,"msg":"NOTLOGIN"} 的 JSON 而不是页面本身。
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.png",
                        "/**/*.jpg",
                        "/**/*.jpeg",
                        "/**/*.gif",
                        "/**/*.webp",
                        "/**/*.ico",
                        "/**/*.svg"
                );
    }
}
