package com.atren.server.mapper;

import com.atren.server.pojo.Collect;
import com.atren.server.pojo.Product;
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
public interface CollectMapper extends BaseMapper<Collect> {

    /**
     * 根据用户id获取用户收藏夹中商品
     * @return
     */
    List<Product> getProductsFromCollect(Integer id);
}
