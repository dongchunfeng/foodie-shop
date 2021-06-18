package com.itzixue.pojo.vo;

import lombok.Data;

/**
 * 购物车显示vo
 */
@Data
public class ShopCatVO {

    private String itemId;
    private String itemName;
    private String itemImgUrl;
    private String specId;
    private String specName;
    private String priceDiscount;
    private String priceNormal;



}
