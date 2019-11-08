package com.xdsty.chatserver.zk;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * description
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/6 10:35
 */
@Component
public class ZkUtil {

    private String zkServer;

    private ZkClient zkClient;

    @Value("${zk.server}")
    public void setZkServer(String zkUrl) {
        zkServer = zkUrl;
    }

    public ZkClient getZkClient(){
        if(zkClient == null){
            zkClient = new ZkClient(zkServer, 6000);
        }
        return zkClient;
    }

}