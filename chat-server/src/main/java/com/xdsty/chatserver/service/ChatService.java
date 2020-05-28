package com.xdsty.chatserver.service;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;

/**
 * description
 *
 * @author 张富华
 * @version 1.0
 * @date 2019/11/5 10:09
 */
public interface ChatService {

    void handMsg(JSONObject param, ChannelHandlerContext ctx);

}
