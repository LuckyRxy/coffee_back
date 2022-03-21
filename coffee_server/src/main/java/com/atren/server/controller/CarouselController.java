package com.atren.server.controller;


import com.atren.server.pojo.Carousel;
import com.atren.server.service.ICarouselService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 *  轮播图
 * </p>
 *
 * @author ren
 * @since 2022-02-10
 */
@RestController
@RequestMapping("/carousel")
public class CarouselController {

    @Autowired
    private ICarouselService carouselService;

    @ApiOperation(value = "获取轮播图地址")
    @GetMapping("/{id}")
    public String getCarouselUrl(@PathVariable Integer id){
        return carouselService.getCarouselUrlById(id);
    }

    @ApiOperation(value = "获取轮播图信息")
    @GetMapping("/info")
    public List<Carousel> getCarousel(){
        return carouselService.list();
    }

}
