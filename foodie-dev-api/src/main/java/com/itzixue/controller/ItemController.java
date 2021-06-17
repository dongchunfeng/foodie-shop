package com.itzixue.controller;


import com.itzixue.pojo.Items;
import com.itzixue.pojo.ItemsImg;
import com.itzixue.pojo.ItemsParam;
import com.itzixue.pojo.ItemsSpec;
import com.itzixue.pojo.vo.CommentLevelCountsVO;
import com.itzixue.pojo.vo.ItemInfoVO;
import com.itzixue.pojo.vo.ShopCatVO;
import com.itzixue.service.ItemService;
import com.itzixue.utils.JSONResult;
import com.itzixue.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "商品接口", tags = {"商品展示相關的接口"})
@RestController
@RequestMapping("/items")
public class ItemController extends BaseController {


    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @RequestMapping("/info/{itemId}")
    public JSONResult info(@ApiParam(name = "itemId", value = "itemId", required = true)
                           @PathVariable String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg(null);
        }
        Items items = itemService.queryItemsById(itemId);
        List<ItemsImg> itemsImgs = itemService.queryItemImgById(itemId);
        List<ItemsSpec> itemsSpecs = itemService.queryItemSpecById(itemId);
        ItemsParam itemsParam = itemService.queryItemParamById(itemId);

        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItem(items);
        itemInfoVO.setItemImgList(itemsImgs);
        itemInfoVO.setItemParams(itemsParam);
        itemInfoVO.setItemSpecList(itemsSpecs);

        return JSONResult.ok(itemInfoVO);
    }

    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级", httpMethod = "GET")
    @RequestMapping("/commentLevel")
    public JSONResult commentLevel(@ApiParam(name = "itemId", value = "商品Id", required = true)
                                   @RequestParam String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg(null);
        }
        CommentLevelCountsVO countsVO = itemService.queryCommentLevelCounts(itemId);
        return JSONResult.ok(countsVO);
    }

    @ApiOperation(value = "查询商品评论信息", notes = "查询商品评论信息", httpMethod = "GET")
    @RequestMapping("/comments")
    public JSONResult comments(@ApiParam(name = "itemId", value = "商品Id", required = true)
                               @RequestParam String itemId,
                               @ApiParam(name = "level", value = "评价等级", required = false)
                               @RequestParam Integer level,
                               @ApiParam(name = "page", value = "评论当前页", required = false)
                               @RequestParam Integer page,
                               @ApiParam(name = "pageSize", value = "当前页的个数", required = false)
                               @RequestParam(value = "pageSize") Integer size) {
        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = COMMENT_PAGE_SIZE;
        }

        PagedGridResult pagedGridResult = itemService.queryPagedComments(itemId, level, page, size);

        return JSONResult.ok(pagedGridResult);
    }

    @ApiOperation(value = "搜索商品信息", notes = "搜索商品信息", httpMethod = "GET")
    @RequestMapping("/search")
    public JSONResult search(@ApiParam(name = "keywords", value = "关键字", required = true)
                               @RequestParam String keywords,
                               @ApiParam(name = "sort", value = "排序方式", required = false)
                               @RequestParam String sort,
                               @ApiParam(name = "page", value = "当前页", required = false)
                               @RequestParam Integer page,
                               @ApiParam(name = "pageSize", value = "当前页的个数", required = false)
                               @RequestParam(value = "pageSize") Integer size) {
        if (StringUtils.isBlank(keywords)) {
            return JSONResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = PAGE_SIZE;
        }

        PagedGridResult pagedGridResult = itemService.querySearchItems(keywords, sort, page, size);

        return JSONResult.ok(pagedGridResult);
    }

    @ApiOperation(value = "根据3级分类搜索商品列表", notes = "根据3级分类搜索商品列表", httpMethod = "GET")
    @RequestMapping("/catItems")
    public JSONResult catItems(@ApiParam(name = "catId", value = "3级分类id", required = true)
                             @RequestParam Integer catId,
                             @ApiParam(name = "sort", value = "排序方式", required = false)
                             @RequestParam String sort,
                             @ApiParam(name = "page", value = "当前页", required = false)
                             @RequestParam Integer page,
                             @ApiParam(name = "pageSize", value = "当前页的个数", required = false)
                             @RequestParam(value = "pageSize") Integer size) {
        if (catId==null) {
            return JSONResult.errorMsg("分类不能为空!");
        }
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = PAGE_SIZE;
        }

        PagedGridResult pagedGridResult = itemService.querySearchItemsByThirdCat(catId, sort, page, size);

        return JSONResult.ok(pagedGridResult);
    }

    //用于用户长时间未登陆，刷新购物车中的数据  主要是商品价格
    @ApiOperation(value = "根据规格id查询商品数据", notes = "根据规格id查询商品数据", httpMethod = "GET")
    @RequestMapping("/refresh")
    public JSONResult refresh(@ApiParam(name = "itemSpecIds", value = "拼接的规格id", required = true,example = "1001,1002,1003")
                               @RequestParam String itemSpecIds) {
        if (StringUtils.isBlank(itemSpecIds)) {
            return JSONResult.errorMsg("商品规格id不能为空!");
        }
        List<ShopCatVO> shopCatVOS = itemService.queryItemsBySpecIds(itemSpecIds);
        return JSONResult.ok(shopCatVOS);
    }


}
