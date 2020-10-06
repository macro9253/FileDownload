package com.macro.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class UserUtils {

    /**
     * 可以使用代码的方式获取配置，写法不同而已
     */


    @Value("${download.username:root}")
    private String usernameYml;
    @Value("${download.password:root}")
    private String passwordYml;
    @Value("${download.name:超级管理员}")
    private String nameYml;

    public  String getUsername(){
        log.info("当前yml配置账号：{}",usernameYml);
            return usernameYml;
    }

    public  String getPassword(){
        log.info("当前yml配置密码：{}",passwordYml);
            return passwordYml;
    }
    public String getName(){
        log.info("当前yml配置名称：{}",nameYml);
            return nameYml;
    }


}
