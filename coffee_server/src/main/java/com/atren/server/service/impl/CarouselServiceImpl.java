package com.atren.server.service.impl;

import com.atren.server.mapper.CarouselMapper;
import com.atren.server.pojo.Carousel;
import com.atren.server.service.ICarouselService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements ICarouselService {

    @Autowired
    private CarouselMapper carouselMapper;
    /**
     * 获取轮播图地址
     * @param id
     * @return
     */
    @Override
    public String getCarouselUrlById(Integer id) {
        return carouselMapper.selectById(id).getImgPath();
    }

}
