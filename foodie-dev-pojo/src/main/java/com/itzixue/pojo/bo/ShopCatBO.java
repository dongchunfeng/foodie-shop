package com.itzixue.pojo.bo;

import lombok.Data;

@Data
public class ShopCatBO {

    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private String specId;
    private String specName;
    private Integer buyCounts;
    private String priceDiscount;
    private String priceNormal;



}
