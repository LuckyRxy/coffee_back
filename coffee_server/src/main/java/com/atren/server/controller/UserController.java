package com.atren.server.controller;

import com.atren.server.pojo.*;
import com.atren.server.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ren
 * @since 2022-02-10
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public RespBean login(@RequestBody UserLoginParam userLoginParam,HttpServletRequest request){
        User user = userService.getUserByUsername(userLoginParam.getUsername());
        if (null==user || !passwordEncoder.matches(userLoginParam.getPassword(),user.getPassword())){
            return RespBean.codeError(408,"用户名或者密码不正确！");
        }
        user.setPassword(null);
        //登录之后将用户信息放入session里面
        request.getSession().setAttribute("user",user);
        return RespBean.success("登录成功！",user);
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public RespBean register(@RequestBody User user){
        System.out.println(user);
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        if (userService.save(user)){
            return RespBean.success("注册成功！");
        }
        return RespBean.error("注册失败！");
    }

    @ApiOperation(value = "判断数据库中是否有此用户名")
    @GetMapping("/findUserName")
    public RespBean findUserName(@RequestParam String username){
        Integer number = 0;
        if (null != username){
            number = userService.findUserName(username);
        }
        if (number > 0){
            return RespBean.registerError("用户名已存在，请重新输入！");
        }

        return RespBean.success("无此用户名！");
    }

    @GetMapping("/failed")
    public RespBean loginFailed(){
        return RespBean.codeError(401,"用户没有登录，请登录后再操作!");
    }

}
