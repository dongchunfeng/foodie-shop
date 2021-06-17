package com.itzixue.service;

import com.itzixue.pojo.Carousel;

import java.util.List;

/**
 * @Description
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/12/9
 */
public interface CarouselService {

    /**
     * 查询所有轮播图
     * @param isShow
     * @return
     */
    List<Carousel> findAll(Integer isShow);

}
