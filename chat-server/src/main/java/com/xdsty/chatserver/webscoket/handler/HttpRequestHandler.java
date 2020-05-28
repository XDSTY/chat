package com.xdsty.chatserver.webscoket.handler;

import com.xdsty.chatserver.attribute.Attributes;
import com.xdsty.chatserver.util.HttpStatus;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static io.netty.handler.codec.http.HttpUtil.isKeepAlive;

/**
 * description
 * @author 张富华
 * @version 1.0
 * @date 2019/11/4 17:19
 */
@Component("httpRequestHandler")
@ChannelHandler.Sharable
public class HttpRequestHandler extends SimpleChannelInboundHandler<Object> {

    private static final Logger log = LoggerFactory.getLogger(HttpRequestHandler.class);

    private static final String WEBSOCKET = "websocket";

    private static final String UPGRADE = "Upgrade";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        //为http连接
        if(msg instanceof FullHttpRequest){
            log.info("收到http请求");
            handlerHttpRequest(ctx, (FullHttpRequest) msg);
        }else{
            //交给下一个处理器处理 SimpleChannelInBoundHandler会自动的清除消息 所以需要手动的retain
            ctx.fireChannelRead(((WebSocketFrame)msg).retain());
        }
    }

    /**
     * 处理http请求，主要用来进行websocket的握手
     */
    private void handlerHttpRequest(ChannelHandlerContext ctx ,FullHttpRequest req){
        //要求http带有Upgrade
        if(!req.decoderResult().isSuccess() || !(WEBSOCKET.equals(req.headers().get(UPGRADE)))){
            //不是建立Upgrade的请求，则返回错误给客户端
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
        }
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                "ws:/"+ ctx.channel() +"/websocket", null, false);
        WebSocketServerHandshaker handShaker = wsFactory.newHandshaker(req);
        if (handShaker == null) {
            WebSocketServerHandshakerFactory
                    .sendUnsupportedVersionResponse(ctx.channel());
        } else {
            //保存连接的handshaker
            ctx.channel().attr(Attributes.HANDSHAKER).set(handShaker);
            handShaker.handshake(ctx.channel(), req);
        }
    }

    /**
     * 处理错误信息
     */
    private static void sendHttpResponse(ChannelHandlerContext ctx,
                                         FullHttpRequest req, DefaultFullHttpResponse res) {
        // 返回应答给客户端
        if (res.status().code() != HttpStatus.OK) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(),
                    CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        // 如果是非Keep-Alive，关闭连接
        if (!isKeepAlive(req) || res.status().code() != HttpStatus.OK) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    /**
     * 发生异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("发生异常，关闭连接", cause);
        ctx.close();
    }
}
