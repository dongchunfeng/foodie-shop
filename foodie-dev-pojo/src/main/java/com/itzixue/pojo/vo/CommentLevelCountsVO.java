package com.itzixue.pojo.vo;

import lombok.Data;

/**
 * 用於展示商品評價數量vo
 */
@Data
public class CommentLevelCountsVO {

    private Integer totalCounts;
    private Integer goodCounts;
    private Integer normalCounts;
    private Integer badCounts;


}
