package com.xdsty.chatserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xdsty.chatserver.service.ChatService;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author 张富华
 * @version 1.0
 * @date 2019/11/5 10:12
 */
@Component
public class ChatServiceImpl  implements ChatService {

    @Override
    public void handMsg(JSONObject param, ChannelHandlerContext ctx) {
        String type = (String) param.get("type");
    }
}
