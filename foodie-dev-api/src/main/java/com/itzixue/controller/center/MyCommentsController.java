package com.itzixue.controller.center;


import com.itzixue.controller.BaseController;
import com.itzixue.enums.YesOrNo;
import com.itzixue.pojo.OrderItems;
import com.itzixue.pojo.Orders;
import com.itzixue.pojo.bo.center.OrderItemsCommentBO;
import com.itzixue.service.center.MyCommentsService;
import com.itzixue.service.center.MyOrdersService;
import com.itzixue.utils.JSONResult;
import com.itzixue.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "用户中心评价", tags = "用户中心评价相关的接口")
@RestController
@RequestMapping("/mycomments")
public class MyCommentsController extends BaseController {

    @Autowired
    private MyCommentsService myCommentsService;


    @ApiOperation(value = "查询评价列表", notes = "查询评价列表", httpMethod = "POST")
    @PostMapping("/pending")
    public JSONResult query(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name="orderId",value = "订单id",required = true)
            @RequestParam String orderId
    ) {

        //检验订单是否属于当前用户
        JSONResult jsonResult = checkUserOrder(userId, orderId);
        if(jsonResult.getStatus()!=HttpStatus.OK.value()){
            return jsonResult;
        }

        Orders myOrder = (Orders)jsonResult.getData();
        if(myOrder.getIsComment()== YesOrNo.YES.type){
            return JSONResult.errorMsg("该订单已评价");
        }
        List<OrderItems> orderItems = myCommentsService.queryPendingComment(orderId);

        return JSONResult.ok(orderItems);
    }

    @ApiOperation(value = "保存评论列表", notes = "保存评论列表", httpMethod = "POST")
    @PostMapping("/saveList")
    public JSONResult saveList(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId,
            @RequestBody List<OrderItemsCommentBO> commentList) {

        System.out.println(commentList);

        // 判断用户和订单是否关联
        JSONResult checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }
        // 判断评论内容list不能为空
        if (commentList == null || commentList.isEmpty() || commentList.size() == 0) {
            return JSONResult.errorMsg("评论内容不能为空！");
        }

        myCommentsService.saveComments(orderId, userId, commentList);
        return JSONResult.ok();
    }

    @ApiOperation(value = "查询我的评价", notes = "查询我的评价", httpMethod = "POST")
    @PostMapping("/query")
    public JSONResult query(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize) {

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult grid = myCommentsService.queryMyComments(userId,
                page,
                pageSize);

        return JSONResult.ok(grid);
    }


}
