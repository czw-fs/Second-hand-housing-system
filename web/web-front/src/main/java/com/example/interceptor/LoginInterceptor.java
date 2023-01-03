package com.example.interceptor;

import com.example.entify.UserInfo;
import com.example.result.Result;
import com.example.result.ResultCodeEnum;
import com.example.util.WebUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: fs
 * @date: 2023/1/2 12:59
 * @Description: everything is ok
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
        if(userInfo == null){
            //证明还没有登录
            Result<String> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
            WebUtil.writeJSON(response,result);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
