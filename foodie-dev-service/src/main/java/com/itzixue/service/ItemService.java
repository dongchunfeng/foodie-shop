package com.itzixue.service;

import com.itzixue.enums.CommentLevel;
import com.itzixue.pojo.Items;
import com.itzixue.pojo.ItemsImg;
import com.itzixue.pojo.ItemsParam;
import com.itzixue.pojo.ItemsSpec;
import com.itzixue.pojo.vo.CommentLevelCountsVO;
import com.itzixue.pojo.vo.ItemCommentVO;
import com.itzixue.pojo.vo.SearchItemsVO;
import com.itzixue.pojo.vo.ShopCatVO;
import com.itzixue.utils.PagedGridResult;

import java.util.List;

public interface ItemService {

    /**
     * 根据商品Id查询详情
     *
     * @param itemId
     * @return
     */
    Items queryItemsById(String itemId);

    /**
     * 根据商品id查询商品图片列表
     *
     * @param itemId
     * @return
     */
    List<ItemsImg> queryItemImgById(String itemId);

    /**
     * 根据商品规格查询商品图片列表
     *
     * @param itemId
     * @return
     */
    List<ItemsSpec> queryItemSpecById(String itemId);


    /**
     * 根据商品Id查询商品参数
     *
     * @param itemId
     * @return
     */
    ItemsParam queryItemParamById(String itemId);

    /**
     * 根據商品id查詢商品評論等級數量
     *
     * @param itemId
     * @return
     */
    CommentLevelCountsVO queryCommentLevelCounts(String itemId);

    /**
     * 根据商品id查询商品评论信息 （分页）
     *
     * @param itemId
     * @param level
     * @return
     */
    PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer size);

    /**
     * 根据关键字和排序 分页 搜索商品
     *
     * @param keywords
     * @param sort
     * @param page
     * @param size
     * @return
     */
    PagedGridResult querySearchItems(String keywords, String sort, Integer page, Integer size);

    /**
     * 根据3级分类id分页查询商品信息
     *
     * @param catId
     * @param sort
     * @param page
     * @param size
     * @return
     */
    PagedGridResult querySearchItemsByThirdCat(Integer catId, String sort, Integer page, Integer size);

    /**
     * 根据规格id查询购物车商品信息
     *
     * @param specIds
     * @return
     */
    List<ShopCatVO> queryItemsBySpecIds(String specIds);

    /**
     * 通过规格id查询规格对象具体信息
     * @param specId
     * @return
     */
    ItemsSpec queryItemBySpecId(String specId);

    /**
     * 根据商品id查询商品主图url
     * @param itemId
     * @return
     */
    String queryItemImgByItemId(String itemId);

    void decreaseItemSpecStock(String specId,int buyCounts);

}
