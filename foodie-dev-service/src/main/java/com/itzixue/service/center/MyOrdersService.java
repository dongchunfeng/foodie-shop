package com.itzixue.service.center;

import com.itzixue.pojo.Orders;
import com.itzixue.utils.PagedGridResult;

public interface MyOrdersService {

    /**
     * 查询我的订单列表
     * @param userId
     * @param orderStatus
     * @param page
     * @param size
     * @return
     */
    PagedGridResult queryMyOrders(String userId,Integer orderStatus,int page,int size);

    /**
     * 订单状态 - >  发货
     * @param orderId
     */
    void updateDeliverOrderStatus(String orderId);

    /**
     * 查询我的订单
     * @param userId
     * @param orderId
     * @return
     */
    Orders queryMyOrder(String userId, String orderId);

    /**
     * 更新订单状态 - > 确认收货
     * @param orderId
     * @return
     */
    boolean updateReceiveOrderStatus(String orderId);

    /**
     * 删除订单  逻辑删除
     * @param orderId
     * @return
     */
    boolean deleteOrder(String orderId,String userId);

}
