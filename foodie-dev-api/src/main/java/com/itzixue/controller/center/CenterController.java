package com.itzixue.controller.center;

import com.itzixue.pojo.Users;
import com.itzixue.service.center.CenterUserService;
import com.itzixue.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "center - 用户中心",tags = "用户中心展示的相关的api")
@RequestMapping("/center")
@RestController
public class CenterController {

    @Autowired
    private CenterUserService centerUserService;

    @ApiOperation(value = "获取用户信息",notes = "获取用户信息",httpMethod = "GET")
    @GetMapping(value = "/userInfo")
    public JSONResult userInfo(
            @ApiParam(name = "userId",value = "用户Id",required = true)
                                           String userId){
        Users users = centerUserService.queryUserById(userId);
        return JSONResult.ok(users);
    }


}
