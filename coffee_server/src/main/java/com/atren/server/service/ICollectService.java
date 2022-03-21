package com.atren.server.service;

import com.atren.server.pojo.Collect;
import com.atren.server.pojo.Product;
import com.atren.server.pojo.RespBean;
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
public interface ICollectService extends IService<Collect> {

    /**
     * 根据用户id获取用户收藏夹中商品
     * @return
     */
    List<Product> getProductsFromCollect(Integer id);

    /**
     * 添加收藏夹信息
     * @param collect
     * @return
     */
    RespBean addCollect(Collect collect);
}
