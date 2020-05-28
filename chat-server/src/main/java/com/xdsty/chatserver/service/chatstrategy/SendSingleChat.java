package com.xdsty.chatserver.service.chatstrategy;

import com.alibaba.fastjson.JSONObject;
import com.xdsty.chatserver.dispatch.ConsistentHash;
import com.xdsty.chatserver.mq.MsgProducer;
import com.xdsty.chatserver.mq.TopicMap;
import com.xdsty.chatserver.msg.ErrMsg;
import com.xdsty.chatserver.msg.MsgType;
import com.xdsty.chatserver.msg.SendSingleMsg;
import com.xdsty.chatserver.util.Constant;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * description 处理点对点通信
 * @author 张富华
 * @version 1.0
 * @date 2019/11/5 10:18
 */
@Component
public class SendSingleChat extends AbstractChatStrategy {

    private static final Logger log = LoggerFactory.getLogger(SendSingleChat.class);

    @Override
    public void dealMsg(JSONObject param, ChannelHandlerContext fromCtx) {
        String fromUserId = param.getString("fromUserId");
        String toUserId = param.getString("toUserId");
        String content = param.getString("content");

        //获取toUser所在的websocket服务器
        String server = ConsistentHash.getServer(toUserId);

        //toUser在当前服务器
        if(Constant.LOCAL_HOST_PORT.equals(server)){
            //判断接受方是否在线
            ChannelHandlerContext toCtx = Constant.getCtx(toUserId);
            if(toCtx == null){
                log.info("{} 不在线", toUserId);
                ErrMsg errMsg = ErrMsg.build().setMsg("对方不在线，请稍后滴滴");
                sendTextMsg(fromCtx.channel(), errMsg.toString());
            }else{
                log.info("向 {} 发送消息", toUserId);
                SendSingleMsg singleMsg = SendSingleMsg.build()
                        .setFromUserId(fromUserId)
                        .setContent(content);
                sendTextMsg(toCtx.channel(), singleMsg.toString());
            }
        }else{
            //toUser在其它服务器
            String topic = TopicMap.getTopicByServer(server);
            if(topic == null) {
                log.info("无法找到服务器{}对应的topic", server);
                return;
            }
            log.info("向topic:{}发送数据",topic);
            MsgProducer.sendMsg(topic, param);
        }
    }

    @Override
    public void dealMqMsg(JSONObject param) {
        String fromUserId = param.getString("fromUserId");
        String toUserId = param.getString("toUserId");
        String content = param.getString("content");

        //查找用户的ChannelHandlerContext
        ChannelHandlerContext toCtx = Constant.getCtx(toUserId);
        if(toCtx != null){
            log.info("向 {} 发送消息", toUserId);
            SendSingleMsg singleMsg = SendSingleMsg.build()
                    .setFromUserId(fromUserId)
                    .setContent(content);
            sendTextMsg(toCtx.channel(), singleMsg.toString());
        }
    }

    @PostConstruct
    public void register(){
        //处理点对点图片消息 和 点对点图片消息
        ChatStrategyFactory.register(MsgType.SINGLE_MSG, this);
        ChatStrategyFactory.register(MsgType.SINGLE_IMG, this);
    }
}
