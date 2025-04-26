package com.wanwan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 玩玩
 * @description
 * @since ${DATE}  ${TIME}
 */
@SpringBootApplication
@MapperScan("com.wanwan.mapper")
public class Admin {
    public static void main(String[] args) {
        SpringApplication.run(Admin.class, args);
    }
}