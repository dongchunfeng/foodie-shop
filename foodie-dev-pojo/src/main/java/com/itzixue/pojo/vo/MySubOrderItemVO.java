package com.itzixue.pojo.vo;

import lombok.Data;

/**
 * 我的订单列表 嵌套商品vo
 */
@Data
public class MySubOrderItemVO {

    private String itemId;
    private String itemName;
    private String itemImg;
    private String itemSpecId;
    private String itemSpecName;
    private String buyCounts;
    private String price;

}
