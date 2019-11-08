package com.xdsty.chatserver.service.chatstrategy;

import com.alibaba.fastjson.JSONObject;
import com.xdsty.chatserver.attribute.Attributes;
import com.xdsty.chatserver.msg.MsgType;
import com.xdsty.chatserver.msg.RegisterMsg;
import com.xdsty.chatserver.util.Constant;
import com.xdsty.chatserver.util.JsonUtil;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * description 处理websockt握手完成后
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/5 17:29
 */
@Component
public class RegisterChat extends AbstractChatStrategy {

    private static final Logger log = LoggerFactory.getLogger(RegisterChat.class);

    @Override
    public void dealMsg(JSONObject param, ChannelHandlerContext ctx) {
        //处理用户建立websocket后的消息
        String userId = param.getString("userId");
        if(userId != null){
            log.info("用户 {} 上线", userId);
            //将用户的channel存储到map中
            Constant.addOnlineUser(userId, ctx);
            //将用户Id存放到channel中，在断开连接时使用
            ctx.channel().attr(Attributes.USERID).set(userId);
            RegisterMsg registerMsg = new RegisterMsg();
            //返回成功注册消息
            sendTextMsg(ctx.channel(), JsonUtil.convertToJson(registerMsg));
        }
    }

    @PostConstruct
    public void register(){
        ChatStrategyFactory.register(MsgType.REGISTER, this);
    }
}
