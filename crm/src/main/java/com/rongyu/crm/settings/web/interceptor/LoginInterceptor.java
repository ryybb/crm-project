package com.rongyu.crm.settings.web.interceptor;

import com.rongyu.crm.commons.contants.Constant;
import com.rongyu.crm.settings.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //当用户未登录时访问资源路径，拦截请求并返回登录页面
        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        if (user == null){
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath());//重定向登录页面
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
