package com.itzixue.mapper;

import com.itzixue.pojo.vo.ShopCatVO;
import com.itzixue.pojo.vo.ItemCommentVO;
import com.itzixue.pojo.vo.SearchItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapperCustom{

      List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String,Object> map);

      List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String,Object> map);

      List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String,Object> map);

      List<ShopCatVO> queryItemsBySpecIds(@Param("paramsList") List specIdsList);

      int decreaseItemSpecStock(@Param("specId") String specId,@Param("pendingCounts") int pendingCounts);

}