package com.atren.server.service.impl;

import com.atren.server.mapper.CollectMapper;
import com.atren.server.mapper.OrderMapper;
import com.atren.server.pojo.Collect;
import com.atren.server.pojo.Product;
import com.atren.server.pojo.RespBean;
import com.atren.server.service.ICollectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * 收藏夹管理
 *
 * @author ren
 * @since 2022-02-10
 */
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements ICollectService {

    @Autowired
    private CollectMapper collectMapper;

    /**
     * 根据用户id获取用户收藏夹中商品
     * @return
     */
    @Override
    public List<Product> getProductsFromCollect(Integer id) {
        return collectMapper.getProductsFromCollect(id);
    }

    /**
     * 添加收藏夹信息
     * @param collect
     * @return
     */
    @Override
    public RespBean addCollect(Collect collect) {
        Collect collectBySelect = collectMapper.selectOne(new QueryWrapper<Collect>()
                .eq("user_id", collect.getUserId())
                .eq("product_id", collect.getProductId()));
        if (null != collectBySelect){
            return RespBean.codeError(400,"该商品已在收藏里，请到我的收藏查看！");
        }

        int i = collectMapper.insert(new Collect(null,collect.getUserId(),collect.getProductId(),new Timestamp(System.currentTimeMillis())));

        if (i==1){
            return RespBean.success("添加成功！");
        }

        return RespBean.codeError(401,"添加失败！");
    }
}
