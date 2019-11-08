package com.xdsty.chatserver.service.chatstrategy;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * description
 *
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/5 10:15
 */
public abstract class AbstractChatStrategy {

    /**
     * 处理消息的接口
     * @param param 消息信息
     * @param ctx channel信息
     */
    public abstract void dealMsg(JSONObject param, ChannelHandlerContext ctx);

    /**
     * 处理mq发送的消息
     */
    public void dealMqMsg(JSONObject param){}

    protected void sendMsg(Channel channel, String msg){
        channel.writeAndFlush(msg);
    }

    protected void sendTextMsg(Channel channel, String msg){
        channel.writeAndFlush(new TextWebSocketFrame(msg));
    }

}
