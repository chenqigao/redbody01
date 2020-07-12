package com.hh.sell.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.hh.sell.pojo.ProductInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductServiceImplTest {
    @Autowired
    private ProductServiceImpl productService;

    @Test
    void findOne() {
        ProductInfo productInfo = productService.findOne("123456");
        Assert.assertEquals("123456",productInfo.getProductId());
    }

    @Test
    void findUpAll() {
        List<ProductInfo> productInfoList = productService.findUpAll();
        Assert.assertNotEquals(0,productInfoList.size());
    }

    /**
     *new PageRequest()已经过时
     */
    @Test
    void findAll() {
        PageRequest request = PageRequest.of(0,2);
        Page<ProductInfo> productInfoPage = productService.findAll(request);
        System.out.println(productInfoPage.getTotalElements());
    }

    @Test
    void save() {
        ProductInfo productInfo = new ProductInfo();
        //设置商品Id
        productInfo.setProductId("123456");
        productInfo.setProductName("皮蛋粥");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("很好喝的哦");
        productInfo.setProductIcon("http://pidan.jpg");
        productInfo.setProductStatus(0);
        //注意：这个商品类目编号一定要在数据库中存在才可以
        productInfo.setCategoryType(2);

        ProductInfo result = productService.save(productInfo);
        Assert.assertNotNull(result);
    }
}
