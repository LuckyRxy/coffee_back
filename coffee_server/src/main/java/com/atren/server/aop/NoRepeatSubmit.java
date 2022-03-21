package com.atren.server.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，用于标记Controller中的提交请求
 * @author rxyLucky
 * @create 2022-03-19 10:13
 */
@Target(ElementType.METHOD) //作用在方法上
@Retention(RetentionPolicy.RUNTIME) //运行时有效
public @interface NoRepeatSubmit {

}
