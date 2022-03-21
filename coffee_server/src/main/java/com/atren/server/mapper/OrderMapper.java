package com.atren.server.mapper;

import com.atren.server.pojo.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ren
 * @since 2022-02-10
 */
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 获取用户的订单id
     * @param userId
     * @return
     */
    List<Long> getOrderGroup(Integer userId);
}
