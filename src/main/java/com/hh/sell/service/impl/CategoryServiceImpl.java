package com.hh.sell.service.impl;

import com.hh.sell.mapper.ProductCategoryRepository;
import com.hh.sell.pojo.ProductCategory;
import com.hh.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hh
 * 商品类目Service层的实现
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    //引入dao层接口实现类
    @Autowired
    private ProductCategoryRepository repository;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return repository.getOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}
