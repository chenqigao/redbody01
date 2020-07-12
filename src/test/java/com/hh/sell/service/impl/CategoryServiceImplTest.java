package com.hh.sell.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.hh.sell.pojo.ProductCategory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    void findOne() {
        ProductCategory productCategory = categoryService.findOne(1);
        Assert.assertEquals(new Integer(1),productCategory.getCategoryId());
    }

    @Test
    void findAll() {
        List<ProductCategory> productCategoryList = categoryService.findAll();
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    void findByCategoryTypeIn() {
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(Arrays.asList(1,2,3,4,5));
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    void save() {
        ProductCategory productCategory = new ProductCategory("男生专享",4);
        ProductCategory result = categoryService.save(productCategory);
        Assert.assertNotNull(result);
    }
}
