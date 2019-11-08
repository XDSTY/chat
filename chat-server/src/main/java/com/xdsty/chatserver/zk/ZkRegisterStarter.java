package com.xdsty.chatserver.zk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * description
 *
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/6 11:07
 */
@Component
public class ZkRegisterStarter {

    @Autowired
    private ZkRegister zkRegister;

    @PostConstruct
    public void init(){
        new Thread(zkRegister).start();
    }

}
