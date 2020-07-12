package com.hh.sell;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoggerTest {
    //注意这里LoggerFactory包别导错了
    //打印当前类LoggerTest的信息
    private final Logger logger
            = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test1(){
        logger.debug("debug...");
        logger.info("info...");
        logger.error("error...");
    }
}


