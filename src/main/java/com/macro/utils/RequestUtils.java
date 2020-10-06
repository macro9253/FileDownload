package com.macro.utils;


import org.springframework.util.StringUtils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

public class RequestUtils {

    /**
     * 生成cookie
     * @param cookies
     * @return
     */
    public static Cookie getCookie(Cookie[] cookies){
        String JSESSIONID = getValue(cookies);
        if(StringUtils.isEmpty(JSESSIONID)){
            JSESSIONID = getCookieValue();
        }
        Cookie cookie = new Cookie("JSESSIONIDS", JSESSIONID);
        cookie.setMaxAge(30 * 60);// 设置为30min
        cookie.setPath("/");
        return cookie;
    }

    /**
     * 检测cookie
     * @param request
     * @return
     */
    public static Integer getUserId(HttpServletRequest request){
        String JSESSIONID = getValue(request.getCookies());
        if(StringUtils.isEmpty(JSESSIONID)){
            return null;
        }
        Integer userId = (Integer) request.getSession().getAttribute(JSESSIONID);
        return userId;
    }

    /**
     * 获取cookie 的value
     * @param cookies
     * @return
     */
    public static String getValue(Cookie[] cookies){
        String JSESSIONID = "";
        if(cookies != null){
            for (Cookie cookie: cookies) {
                if(cookie.getName().equals("JSESSIONID")){
                    JSESSIONID = cookie.getValue();
                    break;
                }
            }
        }
        return JSESSIONID;
    }

    public static String getCookieValue(){
        String cookieValue = getBase64(String.valueOf(new Date().getTime()));
        return cookieValue;
    }

    public static String getBase64(String pwd) {
        String encoded = Base64.getEncoder().encodeToString(pwd.getBytes());
        return encoded;
    }

}
