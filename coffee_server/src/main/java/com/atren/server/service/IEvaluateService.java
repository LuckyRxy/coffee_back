package com.atren.server.service;

import com.atren.server.pojo.Evaluate;
import com.atren.server.pojo.RespBean;
import com.atren.server.pojo.RespPageBean;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ren
 * @since 2022-03-18
 */
public interface IEvaluateService extends IService<Evaluate> {

    /**
     * 获取商品评价信息
     * @param productId
     * @return
     */
    RespPageBean getEvaluateByProductId(Integer productId, Integer currentPage, Integer pageSize);

    /**
     * 添加商品评价
     * @param evaluate
     * @return
     */
    RespBean addEvaluate(Evaluate evaluate);
}
