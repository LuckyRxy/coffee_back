package com.atren.server.controller;


import com.atren.server.pojo.Category;
import com.atren.server.pojo.RespBean;
import com.atren.server.service.ICarouselService;
import com.atren.server.service.ICategoryService;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 *  商品类型管理
 * </p>
 *
 * @author ren
 * @since 2022-02-10
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @ApiOperation(value = "获取商品类别")
    @GetMapping("/")
    public RespBean getCategory(){
        return RespBean.success("获取成功！",categoryService.list());
    }

    @ApiOperation(value = "添加商品类别")
    @PostMapping("/")
    public RespBean addCategory(@RequestBody Category category){
        if (categoryService.save(category)){
            return RespBean.success("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    @ApiOperation(value = "修改商品类别")
    @PutMapping("/")
    public RespBean updateCategory(@RequestBody Category category){
        if (categoryService.updateById(category)){
            return RespBean.success("修改成功！");
        }
        return RespBean.error("修改失败！");
    }
}
