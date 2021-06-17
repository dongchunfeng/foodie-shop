package com.itzixue.service.impl;

import com.itzixue.enums.OrderStatusEnum;
import com.itzixue.enums.YesOrNo;
import com.itzixue.mapper.OrderItemsMapper;
import com.itzixue.mapper.OrderStatusMapper;
import com.itzixue.mapper.OrdersMapper;
import com.itzixue.pojo.*;
import com.itzixue.pojo.bo.SubmitOrderBO;
import com.itzixue.pojo.vo.MerchantOrdersVO;
import com.itzixue.pojo.vo.OrderVO;
import com.itzixue.service.AddressService;
import com.itzixue.service.ItemService;
import com.itzixue.service.OrderService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderItemsMapper orderItemsMapper;
    @Autowired
    private OrderStatusMapper orderStatusMapper;
    @Autowired
    private Sid sid;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ItemService itemService;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public OrderVO createOrder(SubmitOrderBO submitOrderBO) {

        String userId = submitOrderBO.getUserId();
        String addressId = submitOrderBO.getAddressId();
        String itemSpecIds = submitOrderBO.getItemSpecIds();
        Integer payMethod = submitOrderBO.getPayMethod();
        String leftMsg = submitOrderBO.getLeftMsg();
        //包邮费用设置为0
        Integer postAmount = 0;

        String orderId = sid.nextShort();
        UserAddress userAddress = addressService.queryAddress(userId, addressId);

        //新订单保存
        Orders newOrders  = new Orders();
        newOrders.setId(orderId);
        newOrders.setUserId(userId);

        newOrders.setReceiverAddress(userAddress.getProvince()+
                "-"+userAddress.getCity()+"-"+userAddress.getDistrict()+
                " "+userAddress.getDetail());
        newOrders.setReceiverName(userAddress.getReceiver());
        newOrders.setReceiverMobile(userAddress.getMobile());

//        newOrders.setTotalAmount();
//        newOrders.setRealPayAmount();

        newOrders.setPostAmount(postAmount);
        newOrders.setPayMethod(payMethod);
        newOrders.setLeftMsg(leftMsg);
        newOrders.setIsComment(YesOrNo.NO.type);
        newOrders.setIsDelete(YesOrNo.NO.type);
        newOrders.setCreatedTime(new Date());
        newOrders.setUpdatedTime(new Date());
        Integer totalAmount = 0;  //商品原价累计
        Integer realPayAmount=0;  //商品实际支付价格累计
        //循环itemSpecIds 保存订单商品信息表
        String[] split = itemSpecIds.split(",");
        for (String itemSpecId: split) {
            //TODO  整合redis 商品购买的数量重新从购物车中获取
            int buyCounts = 1;

            //通过规格Id查询商品价格
            ItemsSpec itemsSpec = itemService.queryItemBySpecId(itemSpecId);
            totalAmount += itemsSpec.getPriceNormal() * buyCounts;
            realPayAmount += itemsSpec.getPriceDiscount() * buyCounts;
            //根据规格id查询商品信息 和图片
            String itemId = itemsSpec.getItemId();
            Items items = itemService.queryItemsById(itemId);
            String itemsUrl = itemService.queryItemImgByItemId(itemId);

            //保存子订单数据
            String subOrderId = sid.nextShort();
            OrderItems orderItems = new OrderItems();
            orderItems.setId(subOrderId);
            orderItems.setOrderId(orderId);
            orderItems.setItemId(itemId);
            orderItems.setItemName(items.getItemName());
            orderItems.setItemImg(itemsUrl);
            orderItems.setBuyCounts(buyCounts);
            orderItems.setItemSpecId(itemSpecId);
            orderItems.setItemSpecName(itemsSpec.getName());
            orderItems.setPrice(itemsSpec.getPriceDiscount());
            orderItemsMapper.insert(orderItems);

            //在用户提交表单后 需要扣除库存
            itemService.decreaseItemSpecStock(itemSpecId,buyCounts);

        }

        newOrders.setTotalAmount(totalAmount);
        newOrders.setRealPayAmount(realPayAmount);
        ordersMapper.insert(newOrders);
        //保存订单状态表
        OrderStatus waitPayOrderStatus = new OrderStatus();
        waitPayOrderStatus.setOrderId(orderId);
        waitPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        waitPayOrderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(waitPayOrderStatus);

        //4.构建商户订单用于传给支付中心
        MerchantOrdersVO merchantOrdersVO = new MerchantOrdersVO();
        merchantOrdersVO.setMerchantOrderId(orderId);
        merchantOrdersVO.setMerchantUserId(userId);
        merchantOrdersVO.setAmount(realPayAmount+postAmount);
        merchantOrdersVO.setPayMethod(payMethod);

        //构建自定义vo
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(orderId);
        orderVO.setMerchantOrdersVO(merchantOrdersVO);

        return orderVO;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateOrderStatus(String orderId, Integer orderStatus) {

        OrderStatus paidStatus = new OrderStatus();
        paidStatus.setOrderId(orderId);
        paidStatus.setOrderStatus(orderStatus);
        paidStatus.setPayTime(new Date());

        orderStatusMapper.updateByPrimaryKeySelective(paidStatus);
    }

    @Override
    public OrderStatus queryOrderStatusInfo(String orderId) {
        return orderStatusMapper.selectByPrimaryKey(orderId);
    }
}
