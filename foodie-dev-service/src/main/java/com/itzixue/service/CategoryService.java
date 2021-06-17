package com.itzixue.service;

import com.itzixue.pojo.Category;
import com.itzixue.pojo.vo.CategoryVO;
import com.itzixue.pojo.vo.NewItemVO;

import java.util.List;

/**
 * @author MrDong
 */
public interface CategoryService {

    /**
     * 查询根节点的分类
     * @return
     */
    List<Category> findRootLevelCat();

    /**
     * 根据一级分类id查询子类信息
     * @param rootCatId
     * @return
     */
    List<CategoryVO> getSubCatList(Integer rootCatId);

    /**
     * 根据一级分类id查询最新前6个商品
     * @param rootCatId
     * @return
     */
    List<NewItemVO> getNewItemList(Integer rootCatId);

}
