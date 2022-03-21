package com.atren.server.mapper;

import com.atren.server.pojo.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ren
 * @since 2022-02-10
 */
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 获取所有商品（分页）
     * @return
     * @param page
     */
    IPage<Product> getProductByPage(Page<Product> page);

    /**
     * 通过类别名获取相应的商品
     * @param categoryId
     * @return
     */
    List<Product> getProductByCategoryId(Integer categoryId);

    /**
     * 根据商品分类名称获取首页展示的商品信息(分页)
     * @param page
     * @param id
     * @return
     */
    IPage<Product> getProductByPageAndId(Page<Product> page, Integer id);

}
