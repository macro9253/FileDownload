package com.macro;

import com.macro.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
public class FileDownloadApplication {



        public static void main(String[] args) {
            ConfigurableApplicationContext applicationContext = SpringApplication.run(FileDownloadApplication.class, args);
            UserUtils userUtils =  applicationContext.getBean("userUtils",UserUtils.class);
            log.info("账号: {} ,密码: {}",userUtils.getUsername(),userUtils.getPassword());
        }




}
