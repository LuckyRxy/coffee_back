package com.atren.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author rxyLucky
 * @create 2022-02-24 20:00
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

//    @Autowired
//    private LoginInterceptor loginInterceptor;

    /**
     * 设置虚拟资源映射路径，上传图片后不需要再重启服务器即可访问
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String path = "file:E:\\Project\\graduation\\coffee_back\\coffee_server\\src\\main\\resources\\static\\images\\product\\";

        registry.addResourceHandler("/images/product/**").addResourceLocations(path);
    }

}
