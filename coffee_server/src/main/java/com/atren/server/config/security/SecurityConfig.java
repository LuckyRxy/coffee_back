package com.atren.server.config.security;

import com.atren.server.config.jwt.JwtAuthenticationTokenFilter;
import com.atren.server.mapper.UserMapper;
import com.atren.server.pojo.Admin;
import com.atren.server.pojo.User;
import com.atren.server.service.IAdminService;
import com.atren.server.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author rxyLucky
 * @create 2022-02-09 18:22
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private IAdminService adminService;
    @Autowired
    private IUserService userService;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/css/**",
                "/js/**",
                "/images/**",
                "/index.html",
                "favicon.ico",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/captcha",
                "/",
                "/carousel/**",
                "/admin/login",
                "/carousel/**",
                "/product/getPromoInfo",
                "/product/getHotInfo/{categoryName1}/{categoryName2}",
                "/category/**",
                "/product/getProductByCategory",
                "/product/getAllProductForStore",
                "/product/getProductBySearch",
                "/user/login",
                "/user/register",
                "/user/findUserName",
                "/product/getDetails",
                "/shoppingCart/getShoppingCart",
                "/shoppingCart/updateShoppingCart",
                "/shoppingCart/deleteShoppingCart",
                "/shoppingCart/addShoppingCart",
                "/collect/**",
                "/order/**",
                "/evaluate/**"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //因为基于的JWT授权认证模式，不需要csrf防护和session相关的管理
        http.csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //授权管理控制的方法
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                //禁用缓存
                .headers()
                .cacheControl();

        //jwt登录授权过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //添加自定义未登录未授权结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    /**
     * 使得登陆时走自己的登录逻辑
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    /**
     * 自定义UserDetailsService
     * @return
     */
    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            //管理员登录
            Admin admin = adminService.getAdminByUsername(username);
            //用户登录
            User user = userService.getUserByUsername(username);
            if (null != admin){
               return admin;
            }
            if (null != user){
                return user;
            }
            throw new UsernameNotFoundException("用户名或者密码不正确！");
        };
    }

    /**
     * 向容器中注入PasswordEncoder实例
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 将自定义jwt登录授权过滤器注入到容器中
     * @return
     */
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }
}
