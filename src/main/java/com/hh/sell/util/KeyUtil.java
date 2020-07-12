package com.hh.sell.util;

import java.util.Random;

/**
 * 生成唯一的主键：时间+随机数
 */
public class KeyUtil {
    public static synchronized String genUniqueKey(){
        Random random = new Random();
        //生成6位随机数
        int a = random.nextInt(900000) + 100000;

        return System.currentTimeMillis()+String.valueOf(a);
    }
}

