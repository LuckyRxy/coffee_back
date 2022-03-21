package com.atren.server.service.impl;

import com.atren.server.mapper.ShoppingCartMapper;
import com.atren.server.pojo.ShoppingCart;
import com.atren.server.service.IShoppingCartService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ren
 * @since 2022-02-10
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements IShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    /**
     * 查询用户的购物车的某个商品
     * @param userId
     * @param productId
     * @return
     */
    @Override
    public ShoppingCart findShoppingCart(Integer userId, Integer productId) {
        return shoppingCartMapper.selectOne(new QueryWrapper<ShoppingCart>()
                .eq("user_id", userId).eq("product_id", productId));
    }
}

