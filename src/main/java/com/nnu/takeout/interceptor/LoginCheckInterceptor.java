package com.nnu.takeout.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnu.takeout.common.Constants;
import com.nnu.takeout.common.R;
import com.nnu.takeout.common.BaseContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录校验拦截器
 * 后台接口 /employee/** 校验员工登录；前台接口 /user/** 校验用户登录。
 */
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();

        Object loginId = null;
        if (uri.startsWith("/employee/") || uri.startsWith("/category/")
                || uri.startsWith("/dish/") || uri.startsWith("/setmeal/")) {
            loginId = session.getAttribute(Constants.EMPLOYEE_SESSION);
        } else if (uri.startsWith("/user/") || uri.startsWith("/address/") || uri.startsWith("/cart/")) {
            loginId = session.getAttribute(Constants.USER_SESSION);
        } else {
            return true;
        }

        if (loginId != null) {
            BaseContext.setCurrentId(Long.valueOf(loginId.toString()));
            return true;
        }

        // 未登录：返回 JSON 提示，前端据此跳转登录页
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(R.error("NOTLOGIN")));
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        BaseContext.removeCurrentId();
    }
}
