package com.atren.server.mapper;

import com.atren.server.pojo.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ren
 * @since 2022-02-10
 */
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 获取在不同类别商品的数量
     * @return
     * @param id
     */
    Integer getProductNumberInCategory(Integer id);

    /**
     * 通过类别名称获取类别id
     * @return
     */
    Integer selectCategoryIdByCategoryName(String categoryName);
}
