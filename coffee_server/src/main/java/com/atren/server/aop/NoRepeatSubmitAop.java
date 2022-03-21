package com.atren.server.aop;

import com.google.common.cache.Cache;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * aop解析注解-配合google的Cache缓存机制
 * @author rxyLucky
 * @create 2022-03-19 10:15
 */
@Slf4j
@Aspect
@Component
public class NoRepeatSubmitAop {
    @Autowired
    private Cache<String, Integer> cache;

    @Pointcut("@annotation(noRepeatSubmit)")
    public void pointCut(NoRepeatSubmit noRepeatSubmit) {
    }

    @Around("pointCut(noRepeatSubmit)")
    public Object arround(ProceedingJoinPoint pjp, NoRepeatSubmit noRepeatSubmit) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
            HttpServletRequest request = attributes.getRequest();
            String key = sessionId + "-" + request.getServletPath();
            if (cache.getIfPresent(key) == null) {// 如果缓存中有这个url视为重复提交
                Object o = pjp.proceed();
                cache.put(key, 0);
                return o;
            } else {
                log.error("重复请求，请稍后在试试!");
                return null;
            }
        } catch (Throwable e) {
            e.printStackTrace();
            log.error("验证重复提交时出现未知异常!");
            return "{\"code\":-889,\"message\":\"验证重复提交时出现未知异常!\"}";
        }
    }

}
