package com.atren.server.service;

import com.atren.server.pojo.Carousel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ren
 * @since 2022-02-10
 */
public interface ICarouselService extends IService<Carousel> {

    /**
     * 获取轮播图地址
     * @param id
     * @return
     */
    String getCarouselUrlById(Integer id);

}
