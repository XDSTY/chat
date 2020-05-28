package com.xdsty.chatserver.webscoket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * description
 *
 * @author 张富华
 * @version 1.0
 * @date 2019/11/4 21:56
 */
@Component
public class Starter {

    private static final Logger log = LoggerFactory.getLogger(Starter.class);

    @Autowired
    private WebSocketServer webSocketServer;

    private Thread nettyThread;

    @PostConstruct
    public void init(){
        nettyThread = new Thread(webSocketServer);
        nettyThread.start();
        log.info("websocket server启动完成");
    }

    @PreDestroy
    public void close(){
        webSocketServer.close();
        log.info("websocket server停止");
    }
}
