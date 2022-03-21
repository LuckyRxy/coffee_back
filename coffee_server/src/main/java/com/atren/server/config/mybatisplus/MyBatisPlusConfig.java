package com.atren.server.config.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author rxyLucky
 * @create 2022-02-23 16:40
 */
@Configuration
public class MyBatisPlusConfig {
    /**
     * 开启分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
            return new PaginationInterceptor();
    }
}
