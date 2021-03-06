package com.itzixue.controller.center;


import com.itzixue.controller.BaseController;
import com.itzixue.pojo.Orders;
import com.itzixue.pojo.vo.OrderStatusCountsVO;
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

@Api(value = "用户中心我的订单", tags = "用户中心我的订单相关的接口")
@RestController
@RequestMapping("/myorders")
public class MyOrdersController extends BaseController {

    @ApiOperation(value = "获得订单状态数概况", notes = "获得订单状态数概况", httpMethod = "POST")
    @PostMapping("/statusCounts")
    public JSONResult statusCounts(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId) {

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg(null);
        }

        OrderStatusCountsVO result = myOrdersService.getOrderStatusCounts(userId);

        return JSONResult.ok(result);
    }

    @ApiOperation(value = "查询订单动向", notes = "查询订单动向", httpMethod = "POST")
    @PostMapping("/trend")
    public JSONResult trend(
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

        PagedGridResult grid = myOrdersService.getOrdersTrend(userId,
                page,
                pageSize);

        return JSONResult.ok(grid);
    }

    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    @PostMapping("/query")
    public JSONResult query(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderStatus", value = "订单装填", required = false)
            @RequestParam Integer orderStatus,
            @ApiParam(name = "page", value = "当前页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "当前页显示的个数", required = false)
            @RequestParam("pageSize") Integer size
    ) {

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户id不能为空!");
        }

        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = COMMENT_PAGE_SIZE;
        }

        PagedGridResult pagedGridResult = myOrdersService.queryMyOrders(userId, orderStatus, page, size);

        return JSONResult.ok(pagedGridResult);
    }

    @ApiOperation(value = "商家发货",notes = "商家发货",httpMethod = "GET")
    @GetMapping("/deliver")
    public JSONResult deliver(
            @ApiParam(name = "orderId",value = "订单id",required = true)
            @RequestParam String orderId
    )throws Exception{
        if(StringUtils.isBlank(orderId)){
            return JSONResult.errorMsg("订单id不能为空!");
        }
        myOrdersService.updateDeliverOrderStatus(orderId);
        return JSONResult.ok();
    }

    @ApiOperation(value = "用户确认收货",notes = "用户确认收货",httpMethod = "POST")
    @PostMapping("/confirmReceive")
    public JSONResult confirmReceive(
            @ApiParam(name = "orderId",value = "订单id",required = true)
            @RequestParam String orderId,
            @ApiParam(name = "userId",value = "用户id",required = true)
            @RequestParam String userId
    )throws Exception{
        //检查用户和订单是否有关联
        JSONResult jsonResult = checkUserOrder(userId, orderId);

        if(jsonResult.getStatus()!= HttpStatus.OK.value()){
            return jsonResult;
        }

        boolean res = myOrdersService.updateReceiveOrderStatus(orderId);
        if(!res){
            return JSONResult.errorMsg("订单确认收货失败");
        }

        return JSONResult.ok();
    }

    @ApiOperation(value = "用户删除订单",notes = "用户删除订单",httpMethod = "POST")
    @PostMapping("/delete")
    public JSONResult delete(
            @ApiParam(name = "orderId",value = "订单id",required = true)
            @RequestParam String orderId,
            @ApiParam(name = "userId",value = "用户id",required = true)
            @RequestParam String userId
    )throws Exception{

        JSONResult jsonResult = checkUserOrder(userId, orderId);

        if(jsonResult.getStatus()!= HttpStatus.OK.value()){
            return jsonResult;
        }

        boolean res = myOrdersService.deleteOrder(orderId,userId);
        if(!res){
            return JSONResult.errorMsg("订单删除收货失败");
        }

        return JSONResult.ok();
    }



}
