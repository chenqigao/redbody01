package com.hh.sell.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回给前端提示的异常报告消息
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {

    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"库存不正确"),
    ORDERDETAIL_NOR_EXIST(12,"订单不存在"),
    ORDER_STATUS_ERROR(13,"订单信息不正确"),
    ORDER_DETAIL_EMPTY(14,"订单没有商品详情"),
    ORDER_UPDATE_FAIL(15,"更新失败"),
    ORDER_PAY_STATUS_ERROR(16,"订单支付状态不正确"),
    PARAM_ERROR(17,"错误"),
    CART_EMPTY(18,"购物车不能为空"),
    ORDER_OWNER_ERROR(19,"订单的openid不一致"),
    ORDER_NOT_EXIST(20,"查不到该订单"),
    ;

    private Integer code;
    private String message;

}
