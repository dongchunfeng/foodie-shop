package com.itzixue.pojo.vo;

import lombok.Data;

@Data
public class MerchantOrdersVO {

    private String merchantOrderId;  //商户订单号
    private String merchantUserId;  //商户方发起的用户
    private Integer amount;  // 实际支付金额
    private Integer payMethod;// 支付方式
    private String returnUrl;// 支付成功后的回调


}
