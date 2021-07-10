package com.itzixue.controller;

import com.itzixue.pojo.Orders;
import com.itzixue.service.center.MyOrdersService;
import com.itzixue.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * @Description
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/12/1
 */
@Controller
public class BaseController {

    public static final String FOODIE_SHOPCART = "shopcart";

    public static final Integer COMMENT_PAGE_SIZE = 10;

    public static final Integer PAGE_SIZE=20;

    public static final Integer COMMON_PAGE_SIZE = 10;

    //支付中心创建订单的地址
    String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

    //微信支付成功  -> 支付中心 -> 天天吃货平台
    //                          回调通知的url
    String payReturnUrl = "localhost:8088/orders/notifyMerchantOrderPaid";

    public static final String IMAGE_USER_FACE_LOCATION = File.separator + "workspaces"+File.separator +
            "images"+File.separator + "foodie"+File.separator + "face";


    @Autowired
    public MyOrdersService myOrdersService;

    /**
     * 用于验证用户与订单是否有联系  避免用户非法调用
     * @return
     */
    public JSONResult checkUserOrder(String userId, String orderId){
        Orders orders = myOrdersService.queryMyOrder(userId,orderId);
        if(orders==null){
            return JSONResult.errorMsg("订单不存在!");
        }
        return JSONResult.ok(orders);
    }

}
