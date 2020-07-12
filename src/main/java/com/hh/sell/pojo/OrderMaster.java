package com.hh.sell.pojo;

import com.hh.sell.enums.OrderStatuEnum;
import com.hh.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 买家端主表
 */

@Data
@Entity
@DynamicUpdate
@Proxy(lazy=false)
public class OrderMaster {
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
    private Integer orderStatus= OrderStatuEnum.NEW.getCode();

    //支付状态，默认为0：未支付
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;
}

