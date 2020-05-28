package com.xdsty.chatweb.zk;

import com.xdsty.chatweb.disaptch.ConsistentHash;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * description 监听zk里的socket server
 * @author 张富华
 * @version 1.0
 * @date 2019/11/6 17:10
 */
@Component
public class ZkSync {

    private static final Logger log = LoggerFactory.getLogger(ZkSync.class);

    @Autowired
    private ZkUtil zkUtil;

    @Value("${zk.root}")
    private String rootPath;

    /**
     * 同步zk中服务器节点数据
     */
    @PostConstruct
    public void syncData(){
        ZkClient zkClient = zkUtil.getZkClient();
        if(zkClient.exists(rootPath)){
            List<String> servers = zkClient.getChildren(rootPath);
            log.info("初始载入{}", servers);
            //更新hash环
            ConsistentHash.nodeChange(servers);
        }
        //添加监听
        subscribleChildChange(zkClient, rootPath);
    }

    /**
     * 监听子节点变化事件
     */
    private void subscribleChildChange(ZkClient zkClient, String path){
        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                log.info("节点数据发生变化改变{}", currentChilds);
                ConsistentHash.nodeChange(currentChilds);
            }
        });
    }

}
