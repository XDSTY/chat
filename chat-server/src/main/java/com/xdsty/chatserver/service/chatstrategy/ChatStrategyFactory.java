package com.xdsty.chatserver.service.chatstrategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/5 17:30
 */
public class ChatStrategyFactory {

    private static Map<String, AbstractChatStrategy> STRATEGY_MAP = new ConcurrentHashMap<>();

    public static void register(String type, AbstractChatStrategy strategy){
        STRATEGY_MAP.put(type, strategy);
    }

    public static AbstractChatStrategy getChatStrategyByType(String type){
        return STRATEGY_MAP.get(type);
    }

}
