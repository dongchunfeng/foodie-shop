package com.itzixue.controller;

import com.itzixue.enums.YesOrNo;
import com.itzixue.pojo.Carousel;
import com.itzixue.pojo.Category;
import com.itzixue.pojo.vo.CategoryVO;
import com.itzixue.pojo.vo.NewItemVO;
import com.itzixue.service.CarouselService;
import com.itzixue.service.CategoryService;
import com.itzixue.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/12/9
 */
@Api(value = "首页", tags = {"首页展示的相关接口"})
@RestController
@RequestMapping(value = "index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取首页轮播图", notes = "获取首页轮播图", httpMethod = "GET")
    @GetMapping(value = "/carousel")
    public JSONResult getCarousel() {
        List<Carousel> all = carouselService.findAll(YesOrNo.YES.type);
        return JSONResult.ok(all);
    }

    @ApiOperation(value = "获取商品分类(一级分类)", notes = "获取商品分类(一级分类)", httpMethod = "GET")
    @GetMapping(value = "/cats")
    public JSONResult getCats() {
        List<Category> all = categoryService.findRootLevelCat();
        return JSONResult.ok(all);
    }

    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @GetMapping(value = "/subCat/{rootCatId}")
    public JSONResult getCats(
            @ApiParam(name = "rootCatId", value = "一级分类Id", required = true)
            @PathVariable("rootCatId") Integer rootCatId) {

        if (rootCatId == null) {
            return JSONResult.errorMsg("分类不存在");
        }
        List<CategoryVO> subCatList = categoryService.getSubCatList(rootCatId);
        return JSONResult.ok(subCatList);
    }

    @ApiOperation(value = "获取一级分类下的前6个最新商品", notes = "获取一级分类下的前6个最新商品", httpMethod = "GET")
    @GetMapping(value = "/sixNewItems/{rootCatId}")
    public JSONResult getSixNewItems(
            @ApiParam(name = "rootCatId", value = "一级分类Id", required = true)
            @PathVariable("rootCatId") Integer rootCatId) {
        if (rootCatId == null) {
            return JSONResult.errorMsg("分类不存在");
        }
        List<NewItemVO> newItemList = categoryService.getNewItemList(rootCatId);
        return JSONResult.ok(newItemList);
    }


}

