package com.itzixue.controller;

import com.itzixue.pojo.bo.ShopCatBO;
import com.itzixue.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "购物车接口controller",tags = "购物车相关的api")
@RestController
@RequestMapping("shopcart")
public class ShopCatController {

    @ApiOperation(value = "同步购物车",notes = "同步购物车",httpMethod = "POST")
    @PostMapping("/add")
    public JSONResult add(
            @RequestParam String userId,
            @RequestBody ShopCatBO shopCatBO,
            HttpServletRequest request,
            HttpServletResponse response
            ){
        if(StringUtils.isBlank(userId)){
            return JSONResult.errorMap("");
        }
        //TODO 前端用户在登录的情况下 添加商品到购物车 会同步到redis里

        return JSONResult.ok();
    }

    @ApiOperation(value = "从购物车中删除商品",notes = "从购物车中删除商品",httpMethod = "POST")
    @PostMapping("/del")
    public JSONResult del(
            @RequestParam String userId,
            @RequestParam String itemSpecId,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        if(StringUtils.isBlank(userId)|| StringUtils.isBlank(itemSpecId) ){
            return JSONResult.errorMap("参数不能为空!");
        }
        //TODO 用户在页面删除购物车中的商品，如果此时用户登陆的话 需要同步删除redis购物车中的商品

        return JSONResult.ok();
    }

}
