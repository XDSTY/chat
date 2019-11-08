package com.xdsty.chatserver.mq;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;



/**
 * description
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/7 10:55
 */
@SpringBootTest
class MsgProducerTest {

    @Test
    void sendMsg() {
            MsgProducer.sendMsg("test","sss");
    }
}