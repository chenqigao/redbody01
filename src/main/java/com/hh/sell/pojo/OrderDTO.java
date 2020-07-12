package com.hh.sell.pojo;

import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 数据传输对象，在各个层之间传输使用，这里为了加上orderDetailList
 * 因为数据库中没有这个字段，所以不能加在OrderDetail类中
 */
@Data
public class OrderDTO {
    //订单Id
    @Id
    private String orderId;

    //买家名字
    private String buyerName;

    //买家电话
    private String buyerPhone;

    //买家地址
    private String buyerAddress;

    //买家微信openid
    private String buyerOpenid;

    //订单总金额
    private BigDecimal orderAmount;

    //订单状态，默认为0:下单
    private Integer orderStatus;

    //支付状态，默认为0：未支付
    private Integer payStatus;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

    //订单详情列表
    List<OrderDetail> orderDetailList;
}


