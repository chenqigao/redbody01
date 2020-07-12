package com.hh.sell.mapper;

import com.hh.sell.pojo.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//写一个接口继承接口JpaRepository<ProductCategory,Integer>，第一个参数为实体类，第二个参数为主键类型
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {
    //根据商品类目编号列表来查询商品类目
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
