package com.atren.server.controller;


import com.atren.server.pojo.Collect;
import com.atren.server.pojo.Product;
import com.atren.server.pojo.RespBean;
import com.atren.server.service.ICollectService;
import com.atren.server.service.IProductService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * 收藏夹管理
 *
 * @author ren
 * @since 2022-02-10
 */
@RestController
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    private ICollectService collectService;

    @Autowired
    private IProductService productService;

    @ApiOperation(value = "添加收藏夹信息")
    @PostMapping("/addCollect")
    public RespBean addCollect(@RequestBody Collect collect){
        return collectService.addCollect(collect);
    }

    @ApiOperation(value = "获取用户收藏夹商品")
    @GetMapping("/getCollect")
    public RespBean getProductsFromCollect(@RequestParam Integer userId, HttpServletRequest request){
        if (request.getSession().getAttribute("user") == null){
            return RespBean.codeError(403,"用户没有登录，请先登录！");
        }

        List<Collect> collectList = collectService.list(new QueryWrapper<Collect>()
                .eq("user_id", userId));
        if (collectList.isEmpty()){
            return RespBean.codeError(404,"获取失败！");
        }

        List<Product> productList = new ArrayList<>();
        for (Collect collect : collectList) {
            Product product = productService.getById(collect.getProductId());
            productList.add(product);
        }
        return  RespBean.success("获取成功！",productList);
    }

    @ApiOperation(value = "删除收藏夹商品")
    @DeleteMapping("/deleteCollect")
    public RespBean deleteCollect(@RequestBody Collect collect,HttpServletRequest request){
        Integer userId = collect.getUserId();
        Integer productId = collect.getProductId();
        if (request.getSession().getAttribute("user") == null){
            return RespBean.codeError(403,"用户没有登录，请先登录！");
        }

        Collect collect1 = collectService.getOne(new QueryWrapper<Collect>()
                .eq("user_id", userId).eq("product_id", productId));

        if (null != collect1){
            boolean isRemove = collectService.remove(new QueryWrapper<Collect>()
                    .eq("user_id", userId).eq("product_id", productId));

            if (isRemove){
                return RespBean.success("删除成功！");
            }
        }
        return RespBean.codeError(404,"删除失败！");
    }
}
