package com.xdsty.chatserver.webscoket;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/4 21:03
 */
@Component
public class WebSocketChildChannelHandler extends ChannelInitializer<SocketChannel> {

    @Autowired
    private ChannelHandler webSocketHandler;

    @Autowired
    private ChannelHandler httpRequestHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //Http编码解码器
        ch.pipeline().addLast("http-codec", new HttpServerCodec());
        //把http头部和body拼成完整的请求
        ch.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
        //方便大文件传输
        ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
        ch.pipeline().addLast("http-handler", httpRequestHandler);
        ch.pipeline().addLast("websocket-handler", webSocketHandler);
    }
}
