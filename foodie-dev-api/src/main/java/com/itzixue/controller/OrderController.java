package com.itzixue.controller;

import com.itzixue.enums.OrderStatusEnum;
import com.itzixue.enums.PayMethod;
import com.itzixue.pojo.OrderStatus;
import com.itzixue.pojo.bo.SubmitOrderBO;
import com.itzixue.pojo.vo.MerchantOrdersVO;
import com.itzixue.pojo.vo.OrderVO;
import com.itzixue.service.OrderService;
import com.itzixue.utils.CookieUtils;
import com.itzixue.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "订单相关",tags ={"订单相关的api"})
@RestController
@RequestMapping("orders")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation(value = "用户下单",tags = {"用户下单"},httpMethod = "POST")
    @PostMapping("/create")
    public JSONResult create(@RequestBody SubmitOrderBO submitOrderBO
    , HttpServletRequest request, HttpServletResponse response){
        System.out.println(submitOrderBO);
        if(submitOrderBO.getPayMethod()!=PayMethod.WEIXIN.type &&
                submitOrderBO.getPayMethod()!=PayMethod.ALIPAY.type){
            return JSONResult.errorMsg("支付方式不支持!");
        }

        //1.创建订单
        OrderVO orderVO = orderService.createOrder(submitOrderBO);
        String orderId = orderVO.getOrderId();

        //2.创建订单后移除购物车中已经提交的商品
        //TODO  整合redis后 完善购物车中的已经结算的商品清除 同步到前端的cookie
//        CookieUtils.setCookie(request,response,FOODIE_SHOPCART,"",true);
        //3.向支付中心发送当前订单 用于保存支付中心的订单数据
        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();

        //为了方便测试 所有金额改为1分
        merchantOrdersVO.setAmount(1);

        merchantOrdersVO.setReturnUrl(payReturnUrl);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("imoocUserId","imooc");
        httpHeaders.add("password","imooc");

        HttpEntity<MerchantOrdersVO> entity = new HttpEntity<>(merchantOrdersVO,httpHeaders);

        ResponseEntity<JSONResult> jsonResultResponseEntity =
                restTemplate.postForEntity(paymentUrl, entity, JSONResult.class);
        JSONResult paymentResult = jsonResultResponseEntity.getBody();
        if(paymentResult.getStatus()!=200){
            return JSONResult.errorMsg("支付中心创建订单失败,请联系管理员!");
        }
        return JSONResult.ok(orderId);

    }

    //支付中心会回调这个方法修改订单状态
    @PostMapping("/notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId){

        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);

        return HttpStatus.OK.value();
    }


    @PostMapping("/getPaidOrderInfo")
    public JSONResult getPaidOrderInfo(String orderId){
        OrderStatus orderStatus = orderService.queryOrderStatusInfo(orderId);
        return JSONResult.ok(orderStatus);
    }

}
