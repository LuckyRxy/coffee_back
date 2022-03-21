package com.atren.server.service.impl;

import com.atren.server.mapper.CategoryMapper;
import com.atren.server.mapper.ProductMapper;
import com.atren.server.pojo.Category;
import com.atren.server.pojo.Product;
import com.atren.server.pojo.RespPageBean;
import com.atren.server.service.ICategoryService;
import com.atren.server.service.IProductService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
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
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ICategoryService categoryService;

    /**
     * 获取所有商品(分页)
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public RespPageBean getProductByPage(Integer currentPage, Integer pageSize) {
        //开启分页
        Page<Product> page = new Page<>(currentPage, pageSize);
        IPage<Product> productPage = productMapper.getProductByPage(page);
        return new RespPageBean(productPage.getTotal(),productPage.getRecords());
    }

    /**
     * 根据商品分类名称获取首页展示的商品信息
     * @param categoryName
     * @return
     */
    @Override
    public List<Product> getProductInfoByCategoryName(String categoryName) {
        Integer id = categoryMapper.selectCategoryIdByCategoryName(categoryName);
        List<Product> productList = productMapper.getProductByCategoryId(id);
        return productList;
    }

    /**
     * 根据商品分类名称获取首页展示的商品信息(分页)
     * @param categoryName
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public List<Product> getProductInfoByCategoryNameAndPage(String categoryName, Integer currentPage, Integer pageSize) {
        //开启分页
        Page<Product> page = new Page<>(currentPage, pageSize);
        Integer id = categoryMapper.selectCategoryIdByCategoryName(categoryName);
        IPage<Product> productPage = productMapper.getProductByPageAndId(page,id);

        return productPage.getRecords();
    }

    /**
     * 根据分类id,分页获取商品信息
     * @param categoryId
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public RespPageBean getProductByCategory(Integer categoryId, Integer currentPage, Integer pageSize) {
        Page<Product> page = new Page<>(currentPage,pageSize);
        IPage<Product> productIPage = productMapper.getProductByPageAndId(page, categoryId);
        return new RespPageBean(productIPage.getTotal(),productIPage.getRecords());
    }

    /**
     * 根据搜索条件，分页获取商品信息
     * @param search
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public RespPageBean getProductBySearch(String search, Integer currentPage, Integer pageSize) {
        Page<Product> page = new Page<>();
        List<Category> categoryList = categoryService.list();
        Iterator<Category> iterator = categoryList.iterator();
        //如果搜索条件为某个分类名称,直接返回该分类的商品信息
        while (iterator.hasNext()){
            String categoryName = iterator.next().getCategoryName();
            if (search.equals(categoryName)){
                Integer id = categoryMapper.selectCategoryIdByCategoryName(categoryName);
                IPage<Product> productIPage = productMapper.getProductByPageAndId(page, id);
                return new RespPageBean(productIPage.getTotal(),productIPage.getRecords());
            }
        }

        //否则根据输入条件进行模糊查询
        IPage<Product> productIPage = productMapper.selectPage(page,
                new QueryWrapper<Product>().like("product_name","%" + search + "%"));

        return new RespPageBean(productIPage.getTotal(),productIPage.getRecords());
    }
}
