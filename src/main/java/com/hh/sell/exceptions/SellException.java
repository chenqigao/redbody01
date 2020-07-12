package com.hh.sell.exceptions;

import com.hh.sell.enums.ResultEnum;

/**
 * 异常类，根据异常状态报异常
 */
public class SellException extends RuntimeException{
    private Integer code;
    private String message;

    public SellException(ResultEnum resultEnum){
        //将message的内容传入父类的构造方法中
        super(resultEnum.getMessage());
        this.code=resultEnum.getCode();
    }

    public SellException(Integer code, String defaultMessage) {
        //将message的内容传入父类的构造方法中
//        super(resultEnum.getMessage());
//        this.code=resultEnum.getCode();
    }
}
