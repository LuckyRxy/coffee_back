package com.atren.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author rxyLucky
 * @create 2022-02-09 15:19
 */
@SpringBootApplication
@MapperScan("com.atren.server.mapper")
public class CoffeeApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoffeeApplication.class,args);
    }
}
