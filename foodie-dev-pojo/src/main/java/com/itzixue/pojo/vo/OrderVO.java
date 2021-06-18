package com.itzixue.pojo.vo;

import lombok.Data;

/**
 * 发送至支付中心订单的vo
 */
@Data
public class OrderVO {

    private String orderId;
    private MerchantOrdersVO merchantOrdersVO;


}
