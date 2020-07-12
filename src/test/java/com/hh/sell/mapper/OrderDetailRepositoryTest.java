package com.hh.sell.mapper;

import com.hh.sell.pojo.OrderDetail;
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
class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("123456788");
        orderDetail.setOrderId("123455");
        orderDetail.setProductIcon("http://chhahh.jps");
        orderDetail.setProductId("11112");
        orderDetail.setProductName("皮蛋粥");
        orderDetail.setProductPrice(new BigDecimal(1.2));
        orderDetail.setProductQuantity(2);

        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    void findByOrderId() {
        List<OrderDetail> orderDetailList = repository.findByOrderId("123455");
        Assert.assertNotEquals(0,orderDetailList.size());
    }
}
