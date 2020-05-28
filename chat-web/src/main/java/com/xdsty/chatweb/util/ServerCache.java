package com.xdsty.chatweb.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * description
 *
 * @author 张富华
 * @version 1.0
 * @date 2019/11/6 17:16
 */
public class ServerCache {

    private static final int retries = 3;

    /**
     * socket server列表
     */
    private static List<String> socketServers = new ArrayList<>();

    /**
     * 添加websocket服务器
     */
    public static void addServer(String host){
        socketServers.add(host);
    }

    /**
     * 服务器列表变动
     */
    public static void serverChange(List<String> list){
        socketServers = list;
    }

    /**
     * 随机获取服务器 并发问题 重试3次
     */
    public static String getServerByRandom(){
        int retryTimes = 0;
        String result = null;
        while (retryTimes < retries) {
            Random random = new Random(socketServers.size());
            try{
                result = socketServers.get(random.nextInt(socketServers.size()));
                break;
            }catch (Exception e){
                retryTimes ++;
            }
        }
        return result;
    }
}
