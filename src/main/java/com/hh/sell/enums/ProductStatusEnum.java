package com.hh.sell.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * @author hh
 *
 * 定义枚举类型增加程序的可读性
 * 用来描述商品的状态
 */
@Getter
@AllArgsConstructor
public enum ProductStatusEnum {
    UP(0,"在架"),
    DOWN(1,"下架");

    //code表示状态值
    private Integer code;
    //message表示状态值对应状态
    private String message;
}
