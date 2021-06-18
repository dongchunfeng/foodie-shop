package com.itzixue.service.center;

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

}
