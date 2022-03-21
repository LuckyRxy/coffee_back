package com.atren.server.service;

import com.atren.server.pojo.Product;
import com.atren.server.pojo.RespPageBean;
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
public interface IProductService extends IService<Product> {

    /**
     * 获取所有商品(分页)
     * @param currentPage
     * @param pageSize
     * @return
     */
    RespPageBean getProductByPage(Integer currentPage, Integer pageSize);

    /**
     * 根据商品分类名称获取首页展示的商品信息
     * @param categoryName
     * @return
     */
    List<Product> getProductInfoByCategoryName(String categoryName);

    /**
     * 根据商品分类名称获取首页展示的商品信息(分页)
     * @param categoryName
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<Product> getProductInfoByCategoryNameAndPage(String categoryName,Integer currentPage, Integer pageSize);

    /**
     * 根据分类id,分页获取商品信息
     * @param categoryId
     * @param currentPage
     * @param pageSize
     * @return
     */
    RespPageBean getProductByCategory(Integer categoryId, Integer currentPage, Integer pageSize);

    /**
     * 根据搜索条件，分页获取商品信息
     * @param search
     * @param currentPage
     * @param pageSize
     * @return
     */
    RespPageBean getProductBySearch(String search, Integer currentPage, Integer pageSize);
}
