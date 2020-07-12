package com.hh.sell.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayStatusEnum {
    WAIT(0,"等待状态"),
    SUCCESS(1,"支付成功");

    private Integer code;
    private String message;
}
