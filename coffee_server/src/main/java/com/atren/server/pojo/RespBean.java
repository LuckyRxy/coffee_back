package com.atren.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共返回对象
 * @author rxyLucky
 * @create 2022-02-10 13:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespBean {
    private boolean success;
    private long code;
    private String message;
    private Object data;

    /**
     * 成功返回结果
     * @param message
     * @return
     */
    public static RespBean success(String message){
        return new RespBean(true,200,message,null);
    }

    /**
     * 成功返回结果
     * @param message
     * @param data
     * @return
     */
    public static RespBean success(String message,Object data){
        return new RespBean(true,200,message,data);
    }

    /**
     * 失败返回结果
     * @param message
     * @return
     */
    public static RespBean error(String message){
        return new RespBean(false,500,message,null);
    }
    /**
     * 注册错误返回结果
     * @param message
     * @return
     */
    public static RespBean registerError(String message){
        return new RespBean(false,103,message,null);
    }

    /**
     * 失败返回结果
     * @param message
     * @param obj
     * @return
     */
    public static RespBean error(String message,Object obj){
        return new RespBean(false,500,message,obj);
    }

    /**
     * 自定义状态码返回结果
     * @param message
     * @return
     */
    public static RespBean codeError(long code,String message){
        return new RespBean(false,code,message,null);
    }
}
