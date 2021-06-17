package com.itzixue.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description 商品vo
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/12/18 14:42
 */
@Data
public class NewItemVO {

    private Integer rootCatId;
    private String rootCatName;
    private String slogan;
    private String catImage;
    private String bgColor;

    private List<SimpleItemVO> simpleItemList;



}
