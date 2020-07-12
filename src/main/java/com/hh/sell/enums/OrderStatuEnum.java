package com.hh.sell.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatuEnum {
    NEW(0,"新订单"),
    FINISHED(1,"完结"),
    CANCEL(2,"取消");

    private Integer code;
    private String message;
}

