package com.hh.sell.service;

import com.hh.sell.pojo.ProductCategory;

import java.util.List;

/**
 * @author hh
 * 商品类目Service层接口
 */
public interface CategoryService {
    /**
     * 根据categoryId查询单个商品类目
     * @param categoryId
     * @return
     */
    ProductCategory findOne(Integer categoryId);

    /**
     * 查询所有商品类目
     * @return
     */
    List<ProductCategory> findAll();

    /**
     * 根据categoryTypeList查询商品类目
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    /**
     * 添加商品类目
     * @param productCategory
     * @return
     */
    ProductCategory save(ProductCategory productCategory);
}

