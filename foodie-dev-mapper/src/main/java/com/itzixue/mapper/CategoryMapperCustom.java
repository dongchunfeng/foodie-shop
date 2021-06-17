package com.itzixue.mapper;

import com.itzixue.pojo.vo.CategoryVO;
import com.itzixue.pojo.vo.NewItemVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author MrDong
 */
public interface CategoryMapperCustom {

    /**
     * 查询子节点下的分类
     * @param rootCatId
     * @return
     */
    List<CategoryVO> getSubCategoryList(Integer rootCatId);

    /**
     * 根据一级分类查询最新商品
     * @param map
     * @return
     */
    List<NewItemVO> getSixNewItemsLazy(@Param("paramsMap") Map<String,Object> map);


}