package com.atren.server;

import com.atren.server.pojo.Category;
import com.atren.server.service.ICategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author rxyLucky
 * @create 2022-02-10 16:51
 */
public class CoffeeTest {

    @Autowired
    private ICategoryService categoryService;

    @Test
    public void encodeTest(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("ren123");
        System.out.println(encode);
        boolean b = passwordEncoder.matches("ren123", encode);
        System.out.println(b);
    }

    @Test
    public void test(){
        String code = "$2a$10$bgsfxQdq1XAvc.YVer3irePhKJWfcLONfx373HJ5K85BFReGNswBu";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean b = passwordEncoder.matches("123", code);
        System.out.println(b);
    }

    @Test
    public void test01(){
        String code = "$2a$10$/wArvCfOVmef0YyznkZS8OiGSWjA45oasfRu.qTV7G6zyOv1qj4re";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean b = passwordEncoder.matches("ren123...", code);
        System.out.println(b);
    }
    @Test
    public void test02(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String code = passwordEncoder.encode("ren123");
        System.out.println(code);
    }

    @Test
    public void test04(){

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp);

    }

}
