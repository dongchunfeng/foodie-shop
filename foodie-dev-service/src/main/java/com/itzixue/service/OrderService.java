package com.itzixue.service;


import com.itzixue.pojo.OrderStatus;
import com.itzixue.pojo.bo.SubmitOrderBO;
import com.itzixue.pojo.vo.OrderVO;

public interface OrderService {

    /**
     * 用于创建订单相关
     * @param submitOrderBO
     */
    OrderVO createOrder(SubmitOrderBO submitOrderBO);

    /**
     * 修改订单状态
     * @param orderId
     * @param orderStatus
     */
    void updateOrderStatus(String orderId,Integer orderStatus);

    /**
     * 查询订单状态
     * @param orderId
     * @return
     */
    OrderStatus queryOrderStatusInfo(String orderId);

}
