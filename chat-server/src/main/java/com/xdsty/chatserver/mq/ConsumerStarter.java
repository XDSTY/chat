package com.xdsty.chatserver.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * description
 *
 * @author 张富华
 * @version 1.0
 * @date 2019/11/7 15:39
 */
@Component
public class ConsumerStarter {

    @Autowired
    private MsgListener msgListener;

    @PostConstruct
    public void init(){
        new Thread(msgListener).start();
    }
}
