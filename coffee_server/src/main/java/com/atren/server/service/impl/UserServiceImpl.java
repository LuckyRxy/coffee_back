package com.atren.server.service.impl;

import com.atren.server.mapper.UserMapper;
import com.atren.server.pojo.RespBean;
import com.atren.server.pojo.User;
import com.atren.server.service.IUserService;
import com.atren.server.utils.JwtTokenUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ren
 * @since 2022-02-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserMapper userMapper;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 登录之后返回token
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {

        //验证码判断
        String captcha = (String) request.getSession().getAttribute("captcha");
        if (!StringUtils.hasLength(code) || !captcha.equalsIgnoreCase(code)){
            return RespBean.error("验证码错误!");
        }

        //登录操作
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (null==userDetails || !passwordEncoder.matches(password,userDetails.getPassword())){
            return RespBean.error("用户名或者密码不正确！");
        }

        //更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        //将令牌存入Security上下文中
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        return RespBean.success("登录成功",tokenMap);
    }

    /**
     * 获取当前登录用户的信息
     * @param username
     * @return
     */
    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("username",username));
    }

    /**
     * 找到数据库中具有此用户名的数量
     * @param username
     * @return
     */
    @Override
    public Integer findUserName(String username) {
        return userMapper.findUserName(username);
    }
}
