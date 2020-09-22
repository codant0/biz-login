package com.bgtech.bizlogin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.bgtech")
@MapperScan("com.bgtech.bizlogin.db")
public class BizLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(BizLoginApplication.class, args);
    }

}
