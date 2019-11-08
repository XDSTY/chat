package com.xdsty.chatweb.disaptch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * description
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/6 14:30
 */
@Component
public class ConsistentHash {

    private static final Logger log = LoggerFactory.getLogger(ConsistentHash.class);

    /**
     * 虚拟节点,key表示的是hash值，value表示的是虚拟节点
     */
    private static SortedMap<Integer, String> nodes;

    /**
     * 虚拟节点的个数 虚拟节点的格式为 真实节点&&vi 比如127.0.0.1:3333&&v1
     */
    private static final int VIRTUAL_NODES = 10;

    /**
     * 添加websocket服务器
     */
    private static void addServer(Map<Integer, String> map, String host){
        //添加虚拟节点
        for(int i = 0; i < VIRTUAL_NODES; i++){
            String virtualNode = constructVirtualNode(host, i);
            int hash = getHash(virtualNode);
            map.put(hash, host);
        }
        log.info("添加节点{}到hash环上", host);
    }

    /**
     * 删除websocket服务器
     */
    private synchronized static void deleteServer(String host){
        //删除虚拟节点
        for(int i = 0; i < VIRTUAL_NODES; i++){
            String virtualNode = constructVirtualNode(host, i);
            int hash = getHash(virtualNode);
            nodes.remove(hash);
        }
        log.info("删除节点{}", host);
    }

    /**
     * 顺时针获取node节点对应的第一个虚拟节点
     */
    public static String getServer(String node){
        if(node == null){
            return null;
        }

        log.info("hash环大小: {}", nodes.size());
        for(Map.Entry<Integer, String> no : nodes.entrySet()){
            log.info("key: {}, value: {}", no.getKey(), no.getValue());
        }

        int hash = getHash(node);
        SortedMap<Integer, String> tailNodes = nodes.tailMap(hash);
        //获取到的tailNodes可能为空
        if(tailNodes.size() > 0){
            return tailNodes.get(tailNodes.firstKey());
        }
        return nodes.get(nodes.firstKey());
    }

    private static String constructVirtualNode(String host, int i){
        return host + "&&" + i;
    }

    /**
     * FNV1_32_HASH算法，计算hash值
     */
    private static int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash ^ str.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        // 如果算出来的值为负数则取其绝对值
        hash = hash < 0 ? -hash : hash;
        return hash;
    }

    /**
     * 变动节点，生成新的hash环
     */
    public synchronized static void nodeChange(List<String> nodeList){
        SortedMap<Integer, String> map = new TreeMap<>();
        for(String server : nodeList){
            addServer(map, server);
        }
        //更新节点树
        nodes = map;
    }
}