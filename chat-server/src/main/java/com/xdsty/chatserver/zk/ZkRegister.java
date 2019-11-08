package com.xdsty.chatserver.zk;

import com.xdsty.chatserver.dispatch.ConsistentHash;
import com.xdsty.chatserver.util.Constant;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * description 用于获取一致性hash算法的服务节点
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/6 10:05
 */
@Component
public class ZkRegister implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ZkRegister.class);

    @Value("${zk.root}")
    private String zkRoot;

    @Value("${websocket.port}")
    private Integer websocketPort;

    @Autowired
    private ZkUtil zkUtil;

    @Override
    public void run() {
        ZkClient zkClient = zkUtil.getZkClient();
        //判断根节点是否存在
        if(!zkClient.exists(zkRoot)){
            zkClient.create(zkRoot, "", CreateMode.PERSISTENT);
        }

        //创建临时当前节点
        try {
            String ipAndPort = getHostIp() + ":" + websocketPort;

            //保存当前websocket服务器的ip+port
            assignLocalHostPort(ipAndPort);

            String path = zkRoot + "/" + ipAndPort;
            zkClient.create(path, ipAndPort, CreateMode.EPHEMERAL);
            log.info("创建当前临时节点{}", path);

            //获取所有的服务器节点 并注册到本地的hash环上
            List<String> hostList = zkClient.getChildren(zkRoot);
            log.info("创建初始节点{}", hostList);
            //注册到hash环上
            ConsistentHash.nodeChange(hostList);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        //注册监听服务
        subscribeChildEvent(zkClient, zkRoot);
    }

    /**
     * 监听zk子节点变动事件
     */
    private void subscribeChildEvent(ZkClient zkClient, String path){
        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                log.info("节点变更{}",currentChilds);
                //节点变动，更新对应的hash环
                ConsistentHash.nodeChange(currentChilds);
            }
        });
    }

    private String getHostIp() throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();
        return address.getHostAddress();
    }

    private void assignLocalHostPort(String ipAndPort){
        Constant.LOCAL_HOST_PORT = ipAndPort;
    }
}
