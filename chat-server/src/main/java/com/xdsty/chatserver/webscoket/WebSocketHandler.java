package com.xdsty.chatserver.webscoket;

import com.alibaba.fastjson.JSONObject;
import com.xdsty.chatserver.attribute.Attributes;
import com.xdsty.chatserver.msg.ErrMsg;
import com.xdsty.chatserver.msg.MsgType;
import com.xdsty.chatserver.service.chatstrategy.AbstractChatStrategy;
import com.xdsty.chatserver.service.chatstrategy.ChatStrategyFactory;
import com.xdsty.chatserver.util.Constant;
import com.xdsty.chatserver.util.JsonUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * description websocket handler
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/4 17:38
 */
@Component("webSocketHandler")
@ChannelHandler.Sharable
public class WebSocketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private static final Logger log = LoggerFactory.getLogger(WebSocketHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame webSocketFrame) throws Exception {
        log.info("接收到websocket请求");
        //关闭请求
        if(webSocketFrame instanceof CloseWebSocketFrame){
            WebSocketServerHandshaker handshaker = ctx.channel().attr(Attributes.HANDSHAKER).get();
            //将用户从在线map中移除
            String userId = ctx.channel().attr(Attributes.USERID).get();
            log.info("用户: {}，下线", userId);
            Constant.removeOnlineUser(userId);

            if(handshaker == null){
                log.info("连接不存在 {}", ctx.channel().id());
            }else{
                handshaker.close(ctx.channel(), (CloseWebSocketFrame)webSocketFrame.retain());
            }
            return;
        }
        //ping请求
        if(webSocketFrame instanceof PingWebSocketFrame){
            ctx.channel().write(new PongWebSocketFrame(webSocketFrame.retain().content()));
        }
        //暂时只支持文本数据
        if(webSocketFrame instanceof BinaryWebSocketFrame){
            sendErrMsg(ctx, "不支持二进制数据");
            return;
        }
        TextWebSocketFrame textWebSocketFrame = null;
        if(webSocketFrame instanceof TextWebSocketFrame) {
            textWebSocketFrame = (TextWebSocketFrame) webSocketFrame;
        }
        JSONObject param = JsonUtil.convertJson2Obj(textWebSocketFrame.text());
        //处理文本数据
        String type = param.getString("type");
        //找出策略
        AbstractChatStrategy chatStrategy = ChatStrategyFactory.getChatStrategyByType(type);
        if(chatStrategy != null){
            //处理数据
            chatStrategy.dealMsg(param, ctx);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //握手完成
        if(evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE){
            ctx.pipeline().remove(HttpRequestHandler.class);
        }else{
            super.userEventTriggered(ctx, evt);
        }
    }

    private void sendErrMsg(ChannelHandlerContext ctx, String msg){
        ErrMsg errMsg = ErrMsg.build().setCode("50328").setMsg(msg);
        ctx.channel().writeAndFlush(new TextWebSocketFrame(errMsg.toString()));
    }
}
