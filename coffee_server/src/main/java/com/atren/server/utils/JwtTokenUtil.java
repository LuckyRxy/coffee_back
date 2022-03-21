package com.atren.server.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwtToken工具类
 * @author rxyLucky
 * @create 2022-02-10 9:31
 */
@Component
public class JwtTokenUtil {

    //负载里面的用户名
    private static final String CLAIM_KEY_USERNAME = "sub";
    //jwtToken的创建时间
    private static final String CLAIM_KEY_CREATE = "created";
    //jwt加密解密时的密钥
    @Value("${jwt.secret}")
    private String secret;
    //jwtToken的过期时间
    @Value("${jwt.expiration}")
    private Long expiration;


    /**
     * 从token中获取登录用户名
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token){
        String username = null;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }

        return username;
    }


    /**
     * 根据用户信息生成token
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATE,new Date());
        return generateToken(claims);
    }

    /**
     * 判断token是否有效
     * @param token
     * @param userDetails
     * @return
     */
    public Boolean validateToken(String token,UserDetails userDetails){
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否可以被刷新
     * @param token
     * @return
     */
    public boolean canRefresh(String token){
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    public String reFreshToken(String token){
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATE,new Date());
        return generateToken(claims);
    }

    /**
     * 判断token是否过期
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     * @param token
     * @return
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }


    /**
     * 根据负载生成token
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationData())
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    /**
     * 生成token过期时间
     * @return
     */
    private Date generateExpirationData() {
        return new Date(System.currentTimeMillis() + (expiration * 1000) );
    }

    /**
     * 通过token获取负载
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return claims;
    }

}
