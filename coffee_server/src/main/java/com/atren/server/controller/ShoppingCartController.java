package com.atren.server.controller;


import com.atren.server.pojo.Product;
import com.atren.server.pojo.RespBean;
import com.atren.server.pojo.ShoppingCart;
import com.atren.server.pojo.ShoppingCartDateTemp;
import com.atren.server.service.IProductService;
import com.atren.server.service.IShoppingCartService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ren
 * @since 2022-02-10
 */
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private IShoppingCartService shoppingCartService;

    @Autowired
    private IProductService productService;

    @ApiOperation(value = "获取用户购物车信息")
    @GetMapping("/getShoppingCart")
    public RespBean getShoppingCart(@RequestParam Integer userId, HttpServletRequest request){
        if (request.getSession().getAttribute("user") == null){
            return RespBean.codeError(403,"用户没有登录，请先登录！");
        }
        List<ShoppingCart> shoppingCartList= shoppingCartService.list(new QueryWrapper<ShoppingCart>()
                .eq("user_id",userId));
        List<ShoppingCartDateTemp> shoppingCartDateTempList = getShoppingCartData(shoppingCartList);
        if (shoppingCartDateTempList.isEmpty()){
            return RespBean.codeError(404,"获取失败！");
        }

        return RespBean.success("获取成功！",shoppingCartDateTempList);
    }

    @ApiOperation(value = "插入购物车信息")
    @PostMapping("/addShoppingCart")
    public RespBean addShoppingCart(@RequestBody ShoppingCart shoppingCart,HttpServletRequest request){
        Integer userId = shoppingCart.getUserId();
        Integer productId = shoppingCart.getProductId();
        if (request.getSession().getAttribute("user") == null){
            return RespBean.codeError(403,"用户没有登录，请先登录！");
        }

        //判断该用户的购物车是否存在该商品
        ShoppingCart shoppingCartByFind = shoppingCartService.findShoppingCart(userId, productId);

        if (null != shoppingCartByFind){
            //如果存在则把数量+1
            Integer num = shoppingCartByFind.getNum();
            shoppingCartByFind.setNum(num+1);

            Product product = productService.getById(shoppingCartByFind.getProductId());
            //判断数量是否达到商品总数量
            if (shoppingCartByFind.getNum() > product.getProductNum()){
                return RespBean.codeError(409,"数量是否达到商品总数量");
            }

            ShoppingCart cart =
                    new ShoppingCart(shoppingCartByFind.getId(),shoppingCartByFind.getUserId(),
                            shoppingCartByFind.getProductId(),shoppingCartByFind.getNum());

            boolean isUpdate = shoppingCartService.update(cart, new QueryWrapper<ShoppingCart>()
                    .eq("user_id", shoppingCartByFind.getUserId())
                    .eq("product_id", shoppingCartByFind.getProductId()));

            if (isUpdate){
                return RespBean.codeError(202,"该商品已在购物车，数量增加1！");
            }
            return RespBean.error("添加失败！");
        }else {
            //不存在则添加
            ShoppingCart shoppingCartAdd = new ShoppingCart(null, userId, productId, 1);
            // 新插入购物车信息
            boolean isSave = shoppingCartService.save(shoppingCartAdd);
            if (isSave){
                List<ShoppingCart> shoppingCartList= shoppingCartService.list(new QueryWrapper<ShoppingCart>()
                        .eq("user_id",userId));
                List<ShoppingCartDateTemp> shoppingCartDateTempList = getShoppingCartData(shoppingCartList);
                if (shoppingCartDateTempList.isEmpty()){
                    return RespBean.codeError(404,"获取失败！");
                }

                return RespBean.success("添加成功！",shoppingCartDateTempList);
            }

            return RespBean.codeError(005,"添加失败！");
        }



    }

    @ApiOperation(value = "更新购物车商品数量")
    @PutMapping("/updateShoppingCart")
    public RespBean updateShoppingCart(@RequestBody ShoppingCart shopCart,
                                       HttpServletRequest request){
        Integer userId = shopCart.getUserId();
        Integer productId = shopCart.getProductId();
        Integer num = shopCart.getNum();
        if (request.getSession().getAttribute("user") == null){
            return RespBean.codeError(403,"用户没有登录，请先登录！");
        }

        if (num<1){
            return RespBean.codeError(004,"至少购买一个商品！");
        }

        ShoppingCart shoppingCart = shoppingCartService.findShoppingCart(userId,productId);
        if (null != shoppingCart){
            if (shoppingCart.getNum() == num){
                return RespBean.codeError(003,"数量没有发生变化!");
            }

            Product product = productService.getById(productId);
            if (product.getProductNum() < num){
                return RespBean.codeError(004,"数量达到上限");
            }

            ShoppingCart cart =
                    new ShoppingCart(shoppingCart.getId(),shoppingCart.getUserId(),shoppingCart.getProductId(),num);

            boolean isUpdate = shoppingCartService.update(cart, new QueryWrapper<ShoppingCart>()
                    .eq("user_id", shoppingCart.getUserId())
                    .eq("product_id", shoppingCart.getProductId()));

            if (isUpdate){
                return RespBean.success("修改成功！");
            }
        }

        return RespBean.codeError(109,"修改失败！");
    }

    @ApiOperation(value = "删除购物车中的商品")
    @DeleteMapping("/deleteShoppingCart")
    public RespBean deleteShoppingCart(@RequestBody ShoppingCart shopCart,HttpServletRequest request){
        Integer userId = shopCart.getUserId();
        Integer productId = shopCart.getProductId();
        if (request.getSession().getAttribute("user") == null){
            return RespBean.codeError(403,"用户没有登录，请先登录！");
        }

        ShoppingCart shoppingCart = shoppingCartService.findShoppingCart(userId,productId);

        if (null != shoppingCart){

            boolean isRemove = shoppingCartService.remove(new QueryWrapper<ShoppingCart>()
                    .eq("user_id", userId).eq("product_id", productId));
            if (isRemove){
                return RespBean.success("删除成功！");
            }
        }

        return RespBean.codeError(408,"删除失败！");
    }

    /**
     * 生成购物车详细信息
     * @param shoppingCartList
     * @return
     */
    public List<ShoppingCartDateTemp> getShoppingCartData(List<ShoppingCart> shoppingCartList){
        List<ShoppingCartDateTemp> shoppingCartDateTempList = new ArrayList<>();

        for (ShoppingCart shoppingCart : shoppingCartList) {
            Integer productId = shoppingCart.getProductId();
            Product product = productService.getById(productId);

            ShoppingCartDateTemp shoppingCartDateTemp = new ShoppingCartDateTemp();
            shoppingCartDateTemp.setId(shoppingCart.getId());
            shoppingCartDateTemp.setProductId(product.getProductId());
            shoppingCartDateTemp.setProductName(product.getProductName());
            shoppingCartDateTemp.setProductPicture(product.getProductPicture());
            shoppingCartDateTemp.setPrice(product.getProductPrice());
            shoppingCartDateTemp.setNum(shoppingCart.getNum());
            shoppingCartDateTemp.setMaxNum(product.getProductNum());
            shoppingCartDateTemp.setCheck(false);

            shoppingCartDateTempList.add(shoppingCartDateTemp);
        }
        return shoppingCartDateTempList;
    }
}
