package com.itzixue.service.center;

import com.itzixue.pojo.OrderItems;
import com.itzixue.pojo.bo.center.OrderItemsCommentBO;
import com.itzixue.utils.PagedGridResult;

import java.util.List;

/***
 * 我的评论服务实现
 */
public interface MyCommentsService {

    /**
     * 根据订单id查询关联的商品列表
     * @param orderId
     * @return
     */
    List<OrderItems> queryPendingComment(String orderId);

    /**
     * 保存用户的评论
     * @param orderId
     * @param userId
     * @param commentList
     */
    public void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList);


    /**
     * 我的评价查询 分页
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize);


}
