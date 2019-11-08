package com.xdsty.chatserver.webscoket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/4 21:09
 */
@Component
public class WebSocketServer implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * 处理客户端的连接请求
     */
    private EventLoopGroup bossGroup = new NioEventLoopGroup();

    /**
     * 处理客户端的I/O操作
     */
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    @Autowired
    private WebSocketChildChannelHandler webSocketChildChannelHandler;

    private ChannelFuture serverChannelFuture;

    @Value("${netty.port}")
    private Integer port;

    @Override
    public void run() {
        try{
            long begin = System.currentTimeMillis();
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //boss负责处理客户端连接情求 worker负责客户端的读写操作
            serverBootstrap.group(bossGroup, workerGroup)
                    //客户端channel类型
                    .channel(NioServerSocketChannel.class)
                    //握手字符串长度
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //尽可能发送大块数据，减少小块数据
                    .option(ChannelOption.TCP_NODELAY, true)
                    //开启心跳包机制
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //配置固定长度的接受缓冲区
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(592048))
                    .childHandler(webSocketChildChannelHandler);
            long end = System.currentTimeMillis();
            log.info("Netty websocket服务器启动完成，耗时 " + (end - begin) + " ms, ");
            serverChannelFuture = serverBootstrap.bind(port).sync();
        }catch (Exception e){
            log.error(e.getMessage());
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public void close(){
        serverChannelFuture.channel().close();
        Future bossGroupFuture = bossGroup.shutdownGracefully();
        Future workerGroupFuture = workerGroup.shutdownGracefully();

        try{
            bossGroupFuture.await();
            workerGroupFuture.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public ChannelFuture getServerChannelFuture() {
        return serverChannelFuture;
    }

    public void setServerChannelFuture(ChannelFuture serverChannelFuture) {
        this.serverChannelFuture = serverChannelFuture;
    }
}
