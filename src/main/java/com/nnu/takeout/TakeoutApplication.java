package com.nnu.takeout;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 外卖管理系统启动类
 * 项目一《外卖管理系统的设计与实现》
 *
 * @author 潘必凯
 */
@SpringBootApplication
@MapperScan("com.nnu.takeout.mapper")
public class TakeoutApplication {

    public static void main(String[] args) {
        SpringApplication.run(TakeoutApplication.class, args);
        System.out.println("===== 外卖管理系统启动成功，访问 http://localhost:8080/admin/login.html =====");
    }
}
