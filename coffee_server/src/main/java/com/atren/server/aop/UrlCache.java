package com.atren.server.aop;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * 内存缓存配置类
 * @author rxyLucky
 * @create 2022-03-19 10:58
 */
@Configuration
public class UrlCache {

    @Bean
    public Cache<String, Integer> getCache() {
        return CacheBuilder.newBuilder().expireAfterWrite(5L, TimeUnit.SECONDS).build();// 缓存有效期为2秒
    }
}
