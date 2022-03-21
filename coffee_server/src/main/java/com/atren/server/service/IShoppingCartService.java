package com.atren.server.service;

import com.atren.server.pojo.ShoppingCart;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ren
 * @since 2022-02-10
 */
public interface IShoppingCartService extends IService<ShoppingCart> {

    /**
     * 查询用户的购物车的某个商品
     * @param userId
     * @param productId
     * @return
     */
    ShoppingCart findShoppingCart(Integer userId, Integer productId);


}
