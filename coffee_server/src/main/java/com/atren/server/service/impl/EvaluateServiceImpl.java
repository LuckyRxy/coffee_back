package com.atren.server.service.impl;

import com.atren.server.mapper.EvaluateMapper;
import com.atren.server.mapper.UserMapper;
import com.atren.server.pojo.Evaluate;
import com.atren.server.pojo.RespBean;
import com.atren.server.pojo.RespPageBean;
import com.atren.server.pojo.User;
import com.atren.server.service.IEvaluateService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ren
 * @since 2022-03-18
 */
@Service
public class EvaluateServiceImpl extends ServiceImpl<EvaluateMapper, Evaluate> implements IEvaluateService {

    @Autowired
    private EvaluateMapper evaluateMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 获取商品评价信息
     * @param productId
     * @return
     */
    @Override
    public RespPageBean getEvaluateByProductId(Integer productId, Integer currentPage, Integer pageSize) {
        Page<Evaluate> page = new Page<>(currentPage,pageSize);
        Page<Evaluate> evaluatePage =
                evaluateMapper.selectPage(page, new QueryWrapper<Evaluate>().eq("product_id", productId));
        List<Evaluate> evaluateList = evaluatePage.getRecords();
//        List<Evaluate> evaluateList =
//                evaluateMapper.selectList(new QueryWrapper<Evaluate>().eq("product_id", productId));
        Iterator<Evaluate> iterator = evaluateList.iterator();
        while (iterator.hasNext()){
            Evaluate evaluate = iterator.next();
            User user = userMapper.selectById(evaluate.getUserId());
            user.setPassword(null);
            evaluate.setUser(user);
        }
        return new RespPageBean(evaluatePage.getTotal(),evaluateList);
    }

    /**
     * 添加商品评价
     * @param evaluate
     * @return
     */
    @Override
    public RespBean addEvaluate(Evaluate evaluate) {
        evaluate.setCreateTime(new Timestamp(System.currentTimeMillis()));
        int insert = evaluateMapper.insert(evaluate);
        if (1 == insert){
            return RespBean.success("评价成功！");
        }
        return RespBean.codeError(408,"评价失败!");
    }
}
