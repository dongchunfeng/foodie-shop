package com.itzixue.controller;

import com.itzixue.pojo.Users;
import com.itzixue.pojo.bo.UserBO;
import com.itzixue.service.UserService;
import com.itzixue.utils.CookieUtils;
import com.itzixue.utils.JSONResult;
import com.itzixue.utils.JsonUtils;
import com.itzixue.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/12/2
 */
@Api(value="注册登录",tags ={"用户注册登录的接口"})
@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "判断用户名是否存在",notes = "用户名是否存在",httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public JSONResult userNameIsExist(@RequestParam String username) {

        if (StringUtils.isEmpty(username)) {
            return JSONResult.errorMsg("用户名为空!");
        }

        if (userService.queryUserNameIsExist(username)) {
            return JSONResult.errorMsg("用户名已存在");
        }
        return JSONResult.ok();
    }


    @ApiOperation(value = "注册用户",notes = "注册用户",httpMethod = "POST")
    @PostMapping("/regist")
    public JSONResult register(@RequestBody UserBO userBO,HttpServletRequest request, HttpServletResponse response){
        if(StringUtils.isBlank(userBO.getUsername())||
        StringUtils.isBlank(userBO.getPassword())||
        StringUtils.isBlank(userBO.getConfirmPassword())){
            return JSONResult.errorMsg("用户名或者密码不能为空!");
        }

        if(userService.queryUserNameIsExist(userBO.getUsername())){
            return JSONResult.errorMsg("用户名已存在!");
        }

        if(userBO.getPassword().length()< 6 ){
            return JSONResult.errorMsg("密码长度不能小于6!");
        }

        if(!userBO.getPassword().equals(userBO.getConfirmPassword())){
            return JSONResult.errorMsg("两次密码输入不一致!");
        }

        Users userResult = userService.addUser(userBO);

        //清空不需要转换的属性
        userResult = setPropertyNull(userResult);

        //加入cookie
        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(userResult),true);

        return JSONResult.ok();

    }

    @ApiOperation(value = "用户登录",notes = "用户登录",httpMethod = "POST")
    @PostMapping("/login")
    public JSONResult login(@RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String username = userBO.getUsername();
        String password = userBO.getPassword();

        if(StringUtils.isBlank(username)||StringUtils.isBlank(password)){
            return JSONResult.errorMsg("用户名或者密码不能为空!");
        }

        Users userResult = userService.login(username, MD5Utils.getMD5Str(password));
        if(userResult==null){
            return JSONResult.errorMsg("用户名或者密码错误!");
        }

        //清空不需要转换的属性
        userResult = setPropertyNull(userResult);

        //加入cookie
        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(userResult),true);

        return JSONResult.ok(userResult);
    }

    private Users setPropertyNull(Users userResult){
        userResult.setPassword(null);
        userResult.setRealname(null);
        userResult.setEmail(null);
        userResult.setMobile(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }


    @ApiOperation(value = "用户退出登录",notes = "用户退出登录",httpMethod = "POST")
    @PostMapping("/logout")
    public JSONResult layout(@RequestParam String userId,HttpServletRequest request,HttpServletResponse response){

        CookieUtils.deleteCookie(request,response,"user");

        //TODO  用户退出登录需要清空购物车
        //TODO 分布式会话中需要清除用户数据
        return JSONResult.ok();
    }


}
