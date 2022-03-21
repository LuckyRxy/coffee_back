package com.atren.server.service;

import com.atren.server.pojo.Admin;
import com.atren.server.pojo.RespBean;
import com.atren.server.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ren
 * @since 2022-02-21
 */
public interface IAdminService extends IService<Admin> {
    /**
     * 登录之后返回token
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    RespBean login(String username, String password, String code, HttpServletRequest request);

    /**
     * 获取当前登录用户的信息
     * @param username
     * @return
     */
    Admin getAdminByUsername(String username);

}
