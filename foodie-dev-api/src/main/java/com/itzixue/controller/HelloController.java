package com.itzixue.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/12/1
 */
@RestController
public class HelloController {

    @GetMapping(path = "/hello")
    public String hello(){
        return  "hello world!!!";
    }

}
