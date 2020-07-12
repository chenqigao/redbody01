package com.hh.sell.mapper;

import com.hh.sell.pojo.OrderMaster;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
class OrderMasterRepositoryTest {

    @Autowired
    OrderMasterRepository repository;

    private final String OPENID = "110110";

    //测试添加买家端信息方法
    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123456");
        orderMaster.setBuyerName("师兄");
        orderMaster.setBuyerPhone("13756235489");
        orderMaster.setBuyerAddress("北京");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(2.5));
        //因为订单状态和支付状态为默认的，因此不需要设置

        OrderMaster result = repository.save(orderMaster);
        Assert.assertNotNull(result);
    }

    //根据买家微信openid查询买家端信息
    @Test
    void findByBuyerOpenid() {
        PageRequest request = PageRequest.of(0,1);
        Page<OrderMaster> result
                = repository.findByBuyerOpenid(OPENID, request);
        Assert.assertNotEquals(0,result.getTotalElements());
        //System.out.println(result.getTotalElements());
    }


}
