package com.atren.server.service.impl;

import com.atren.server.mapper.CategoryMapper;
import com.atren.server.pojo.Category;
import com.atren.server.service.ICategoryService;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 删除商品类别
     * @param id
     * @return
     */
    @Override
    public Boolean deleteCategoryById(Integer id) {
        Integer number = categoryMapper.getProductNumberInCategory(id);
        if(0 >= number){
            categoryMapper.deleteById(id);
            return true;
        }
        return false;
    }
}
