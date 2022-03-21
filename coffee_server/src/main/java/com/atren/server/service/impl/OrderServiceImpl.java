package com.atren.server.service.impl;

import com.atren.server.mapper.OrderMapper;
import com.atren.server.mapper.ProductMapper;
import com.atren.server.mapper.ShoppingCartMapper;
import com.atren.server.pojo.Order;
import com.atren.server.pojo.OrderParam;
import com.atren.server.pojo.Product;
import com.atren.server.pojo.ShoppingCart;
import com.atren.server.service.IOrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ren
 * @since 2022-02-10
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private ProductMapper productMapper;

    /**
     * 添加订单
     * @param orderParam
     * @return
     */
    @Override
    public boolean addOrder(OrderParam orderParam) {
        //获取购物车中的商品
        List<Product> products = orderParam.getProducts();

        for (Product product : products) {
            Product productReal = productMapper.selectById(product.getProductId());
            product.setProductNum(productReal.getProductNum());
            product.setCategoryId(productReal.getCategoryId());
            product.setProductTitle(productReal.getProductTitle());
            product.setProductIntro(productReal.getProductIntro());
            product.setProductPrice(productReal.getProductPrice());
            product.setProductSales(productReal.getProductSales());
        }
        System.out.println(products);
        //生成订单编号
        long orderId = orderParam.getUserId() + System.currentTimeMillis();

        for (Product product : products) {
            Order order = new Order(null, orderId, orderParam.getUserId(),
                    product.getProductId(), product.getNum(), product.getPrice(),
                    new Timestamp(System.currentTimeMillis()),null,null);

            //插入到数据库
            int insert = orderMapper.insert(order);

            if (insert <= 0){
                return false;
            }

            product.setProductNum(product.getProductNum()-product.getNum());

            //结算成功，从商品表中更新对应的商品数量
            productMapper.updateById(product);
        }

        //插入成功，删除购物车
        for (Product product : products) {
            int idDelete = shoppingCartMapper.delete(new QueryWrapper<ShoppingCart>()
                    .eq("user_id", orderParam.getUserId())
                    .eq("product_id", product.getProductId()));

            if (idDelete <= 0){
                return false;
            }
        }
        return true;
    }

    /**
     * 获取用户的订单详细信息
     * @param userId
     * @return
     */
    @Override
    public List<Order> getUserOrder(Integer userId) {
        List<Order> orderList = orderMapper.selectList(new QueryWrapper<Order>()
                .eq("user_id", userId).orderByDesc("order_time"));
        if (orderList.isEmpty()){
            return null;
        }
        return orderList;
    }

    /**
     * 获取用户的订单id
     * @param userId
     * @return
     */
    @Override
    public List<Long> getOrderGroup(Integer userId) {
        List<Long> orderIdList = orderMapper.getOrderGroup(userId);
        if (orderIdList.isEmpty()){
            return null;
        }
        return orderIdList;
    }
}
