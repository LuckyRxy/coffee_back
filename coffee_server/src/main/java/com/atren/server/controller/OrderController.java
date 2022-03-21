package com.atren.server.controller;


import com.atren.server.pojo.Order;
import com.atren.server.pojo.OrderParam;
import com.atren.server.pojo.Product;
import com.atren.server.pojo.RespBean;
import com.atren.server.service.IOrderService;
import com.atren.server.service.IProductService;
import io.swagger.annotations.Api;
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
 * 订单管理
 *
 * @author ren
 * @since 2022-02-10
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IProductService productService;

    @ApiOperation(value = "获取所有订单")
    @GetMapping("/")
    public List<Order> getOrders(){
        return orderService.list();
    }

    @ApiOperation(value = "修改订单信息")
    @PutMapping("/")
    public RespBean updateOrder(@RequestBody Order order){
        if (orderService.updateById(order)){
            return RespBean.success("修改成功！");
        }
        return RespBean.error("修改失败！");
    }

    @ApiOperation(value = "获取用户的订单信息")
    @GetMapping("/getOrder")
    public RespBean getUserOrder(@RequestParam Integer userId,HttpServletRequest request){
        if (request.getSession().getAttribute("user") == null){
            return RespBean.codeError(403,"用户没有登录，请先登录！");
        }

        //获取所有的订单id
        List<Long> orderIdList = orderService.getOrderGroup(userId);
        if (null == orderIdList){
            return RespBean.codeError(404,"该用户没有订单信息");
        }

        //获取所有的订单详细信息
        List<Order> orderList = orderService.getUserOrder(userId);

        if (null != orderList){
            //在order中添加上商品名称和图片信息
            for (Order order : orderList) {
                Product product = productService.getById(order.getProductId());
                order.setProductName(product.getProductName());
                order.setProductPicture(product.getProductPicture());
            }
            //存放orderId分组之后的集合
            List<ArrayList<Order>> orderListGroup =  new ArrayList<>();
            //将orderId相同的放在一起
            for (Long orderId : orderIdList) {
                //用于接收orderId相同的订单
                ArrayList<Order> orderSameList = new ArrayList<>();

                for (Order order : orderList) {

                    if (orderId.equals(order.getOrderId())){
                        orderSameList.add(order);
                    }
                }

                orderListGroup.add(orderSameList);
            }
            return RespBean.success("获取成功！",orderListGroup);
        }

        return RespBean.codeError(404,"获取失败！");
    }

    @ApiOperation(value = "添加订单信息（结账）")
    @PostMapping("/addOrder")
    public RespBean addOrder(@RequestBody OrderParam orderParam, HttpServletRequest request){
        if (request.getSession().getAttribute("user") == null){
            return RespBean.codeError(403,"用户没有登录，请先登录！");
        }
        boolean isAdd = orderService.addOrder(orderParam);
        if (isAdd){
            return RespBean.success("结算成功！");
        }
        return RespBean.codeError(404,"结算失败！");
    }
}
