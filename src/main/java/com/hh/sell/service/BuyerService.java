package com.hh.sell.service;

import com.hh.sell.pojo.OrderDTO;

public interface BuyerService {
    //查询一个订单
    OrderDTO findOrderOne(String openid, String orderId);

    //取消订单
    OrderDTO cancleOrder(String openid,String orderId);
}
