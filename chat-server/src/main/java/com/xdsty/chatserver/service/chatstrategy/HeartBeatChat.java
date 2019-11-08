package com.xdsty.chatserver.service.chatstrategy;

import com.alibaba.fastjson.JSONObject;
import com.xdsty.chatserver.msg.HeartBeatMsg;
import com.xdsty.chatserver.msg.MsgType;
import com.xdsty.chatserver.util.JsonUtil;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * description 心跳包处理
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/8 14:31
 */
@Component
public class HeartBeatChat extends AbstractChatStrategy {

    private static final Logger log = LoggerFactory.getLogger(HeartBeatChat.class);

    @Override
    public void dealMsg(JSONObject param, ChannelHandlerContext ctx) {
        log.info("接收到心跳消息");
        sendTextMsg(ctx.channel(), JsonUtil.convertToJson(new HeartBeatMsg()));
    }

    @PostConstruct
    public void register(){
        ChatStrategyFactory.register(MsgType.HEART_BEAT, this);
    }
}
