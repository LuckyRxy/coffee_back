package com.atren.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用接收前端传来的订单信息实体类
 *
 * @author rxyLucky
 * @create 2022-03-02 13:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderParam {
    private Integer userId;
    private List<Product> products;
}
