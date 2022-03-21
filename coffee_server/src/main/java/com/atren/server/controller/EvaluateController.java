package com.atren.server.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atren.server.aop.NoRepeatSubmit;
import com.atren.server.pojo.Evaluate;
import com.atren.server.pojo.RespBean;
import com.atren.server.pojo.RespPageBean;
import com.atren.server.service.IEvaluateService;
import com.fasterxml.jackson.core.JsonParser;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ren
 * @since 2022-03-18
 */
@RestController
@RequestMapping("/evaluate")
public class EvaluateController {

    @Autowired
    private IEvaluateService evaluateService;

    @ApiOperation(value = "获取商品评价信息")
    @GetMapping("/getEvaluate")
    public RespBean getEvaluate(@RequestParam Integer productId,
                                @RequestParam Integer currentPage,
                                @RequestParam Integer pageSize){
        System.out.println("1111"+productId);
        RespPageBean evaluateByPage = evaluateService.getEvaluateByProductId(productId, currentPage, pageSize);
        if (null != evaluateByPage){
            return RespBean.success("获取成功！",evaluateByPage);
        }
        return RespBean.codeError(200,"商品暂时没有评价信息！");
    }

    @NoRepeatSubmit
    @ApiOperation(value = "添加评价内容")
    @PostMapping("/addEvaluate")
    public RespBean addEvaluate(@RequestBody Evaluate evaluate){
        return evaluateService.addEvaluate(evaluate);
    }
}
