package com.atren.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 *  与前端中vuex里管理的shoppingCart相对应
 *
 * @author rxyLucky
 * @create 2022-03-01 10:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartDateTemp {

    private Integer id;
    private Integer productId;
    private String productName;
    private String productPicture;
    private double price;
    private Integer num;
    private Integer maxNum;
    private boolean check = false;
}
