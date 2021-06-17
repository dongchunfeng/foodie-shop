package com.itzixue.pojo.vo;

/**
 * @Description
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/12/9
 */

import lombok.Data;

import java.util.List;

/**
 * 二级分类vo(数据层封装)
 * @author MrDong
 */
@Data
public class CategoryVO {

    private Integer id;
    private String name;
    private String type;
    private Integer fatherId;

    /**
     * 三级分类vo
     */
    private List<SubCategoryVO> subCategoryVOList;


}
