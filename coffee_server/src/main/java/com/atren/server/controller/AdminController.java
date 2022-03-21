package com.atren.server.controller;

import com.atren.server.pojo.Admin;
import com.atren.server.pojo.AdminLoginParam;
import com.atren.server.pojo.RespBean;
import com.atren.server.pojo.User;
import com.atren.server.service.IAdminService;
import com.atren.server.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ren
 * @since 2022-02-21
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request){
        return adminService
                .login(adminLoginParam.getUsername(),adminLoginParam.getPassword(),adminLoginParam.getCaptcha(),request);
    }


    @ApiOperation(value = "获取当前用户的信息")
    @GetMapping("/info")
    public RespBean getAdminInfo(Principal principal){
        if (null == principal){
            return null;
        }
        String username = principal.getName();
        Admin admin = adminService.getAdminByUsername(username);
        admin.setPassword(null);
        return RespBean.success("获取成功",admin);
    }

    @ApiOperation(value = "退出操作")
    @PostMapping("/logout")
    public RespBean logout(){
        return RespBean.success("注销成功！");
    }
}
