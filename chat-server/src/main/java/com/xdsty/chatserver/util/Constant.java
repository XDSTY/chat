package com.xdsty.chatserver.util;

import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description
 *
 * @author 张富华
 * @version 1.0
 * @date 2019/11/4 17:41
 */
public class Constant {

    /**
     * 存储在线用户的id和channel
     */
    private static Map<String, ChannelHandlerContext> ONLINE_USER_MAP = new ConcurrentHashMap<>();

    public static void addOnlineUser(String userId, ChannelHandlerContext ctx){
        ONLINE_USER_MAP.put(userId, ctx);
    }

    public static void removeOnlineUser(String userId){
        ONLINE_USER_MAP.remove(userId);
    }

    public static ChannelHandlerContext getCtx(String userId){
        return ONLINE_USER_MAP.get(userId);
    }


    public static String LOCAL_HOST_PORT = null;
}
