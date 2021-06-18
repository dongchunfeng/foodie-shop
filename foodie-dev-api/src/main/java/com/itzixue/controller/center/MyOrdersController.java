package com.itzixue.controller.center;


import com.itzixue.controller.BaseController;
import com.itzixue.service.center.MyOrdersService;
import com.itzixue.utils.JSONResult;
import com.itzixue.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "用户中心我的订单", tags = "用户中心我的订单相关的接口")
@RestController
@RequestMapping("/myorders")
public class MyOrdersController extends BaseController {

    @Autowired
    private MyOrdersService myOrdersService;


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


}
