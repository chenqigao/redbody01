package com.hh.sell.mapper;

import com.hh.sell.pojo.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 查询的流程：
 * 1.先到OrderMaster里面去查询，获取orderId
 * 2.通过orderId到OrderDetail里面去查询
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {
    /**
     * orderMaster里面的一条记录可能对应orderDetail里面的多条记录，因此使用List
     * @param orderId
     * @return
     */
    List<OrderDetail> findByOrderId(String orderId);
}

