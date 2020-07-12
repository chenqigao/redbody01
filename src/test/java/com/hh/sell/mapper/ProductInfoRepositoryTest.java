package com.hh.sell.mapper;

import com.hh.sell.pojo.ProductInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductInfoRepositoryTest {
    @Autowired
    private ProductInfoRepository repository;

    //测试添加商品
    @Test
    public void saveTest(){
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

        ProductInfo result = repository.save(productInfo);
        Assert.assertNotNull(result);
    }

    //测试根据商品状态查看商品是否上架
    @Test
    void findByProductStatus() {
        List<ProductInfo> productInfoList = repository.findByProductStatus(0);
        Assert.assertNotEquals(0,productInfoList.size());
    }
}
