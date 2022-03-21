package com.atren.server.service;

import com.atren.server.pojo.Order;
import com.atren.server.pojo.OrderParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ren
 * @since 2022-02-10
 */
public interface IOrderService extends IService<Order> {

    /**
     * 添加订单
     * @param orderParam
     * @return
     */
    boolean addOrder(OrderParam orderParam);

    /**
     * 获取用户的订单详细信息
     * @param userId
     * @return
     */
    List<Order> getUserOrder(Integer userId);

    /**
     * 获取用户的订单id
     * @param userId
     * @return
     */
    List<Long> getOrderGroup(Integer userId);
}
