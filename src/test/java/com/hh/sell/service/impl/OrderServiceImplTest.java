package com.hh.sell.service.impl;

import com.hh.sell.enums.OrderStatuEnum;
import com.hh.sell.enums.PayStatusEnum;
import com.hh.sell.pojo.OrderDTO;
import com.hh.sell.pojo.OrderDetail;
import com.hh.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    private final String BUYER_OPENID = "110110";
    private final String ORDER_ID = "1593625902940342164";

    @Test
    void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("师兄");
        orderDTO.setBuyerAddress("北京");
        orderDTO.setBuyerPhone("137659863");
        orderDTO.setBuyerOpenid("110110");

        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        //数据库中必须存在
        o1.setProductId("123458");
        o1.setProductQuantity(1);
        orderDetailList.add(o1);

        OrderDetail o2 = new OrderDetail();
        //数据库中必须存在
        o2.setProductId("123457");
        o2.setProductQuantity(2);
        orderDetailList.add(o2);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        log.info("创建订单 result={}",result);
    }

    @Test
    void findOnes() {
        OrderDTO result = orderService.findOnes(ORDER_ID);
        log.info("查询单个订单 result={}",result);
        Assert.assertEquals(ORDER_ID,result.getOrderId());
    }

    @Test
    void findList() {
        PageRequest request = PageRequest.of(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID,request);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    @Test
    void cancel(){
        OrderDTO orderDTO = orderService.findOnes(ORDER_ID);
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatuEnum.CANCEL.getCode(),result.getOrderStatus());

    }

    @Test
    void finish(){
        OrderDTO orderDTO = orderService.findOnes(ORDER_ID);
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatuEnum.FINISHED.getCode(),result.getOrderStatus());

    }

    @Test
    void paid() {
        OrderDTO orderDTO = orderService.findOnes(ORDER_ID);
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
    }

}
