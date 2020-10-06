package com.macro.annotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 检测方法中是否使用@LoginCookie注解：
 *      有则检测cookie，拿到cookie去获取session值
 */
@Aspect//来定义一个切面
@Component
@Slf4j
public class LoginDetect {

    @Autowired
    private HttpServletRequest request;

    @Pointcut("@annotation(com.macro.annotation.LoginCookie)")
    public void LoginFilterCut(){}


    @Around("LoginFilterCut()")
    public Object trackInfo(ProceedingJoinPoint point) throws Throwable {
        //固定的
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        LoginCookie usesCheck = method.getAnnotation(LoginCookie.class);
        if(usesCheck != null){
            String JSESSIONIDS = null;
            Cookie[] cookies = request.getCookies();
            if(cookies != null){
            for (Cookie cookie: cookies) {
                if(cookie.getName().equals("JSESSIONIDS")){
                    JSESSIONIDS = cookie.getValue();
                    break;
                }
            }
            }
            String sessionVal = (String) request.getSession().getAttribute(JSESSIONIDS);
            if(StringUtils.isEmpty(sessionVal)) {
                log.info("-------------没有登录-------------");
                return new ModelAndView("login");
            }
        }
        return point.proceed();
    }
}
