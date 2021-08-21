package com.itzixue.mapper;

import com.itzixue.pojo.OrderStatus;
import com.itzixue.pojo.vo.MyOrdersVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author MrDong
 */
public interface OrdersMapperCustom {

    List<MyOrdersVO> queryMyOrder(@Param("paramsMap") Map<String, Object> map);

    public int getMyOrderStatusCounts(@Param("paramsMap") Map<String, Object> map);

    public List<OrderStatus> getMyOrderTrend(@Param("paramsMap") Map<String, Object> map);

}