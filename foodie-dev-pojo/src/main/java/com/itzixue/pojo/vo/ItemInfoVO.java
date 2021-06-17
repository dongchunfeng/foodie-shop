package com.itzixue.pojo.vo;

import com.itzixue.pojo.Items;
import com.itzixue.pojo.ItemsImg;
import com.itzixue.pojo.ItemsParam;
import com.itzixue.pojo.ItemsSpec;
import lombok.Data;

import java.util.List;

@Data
public class ItemInfoVO {

    Items item;
    List<ItemsImg> itemImgList;
    List<ItemsSpec> itemSpecList;
    ItemsParam itemParams;

}
