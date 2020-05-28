package com.xdsty.chatserver.webscoket.handler;

import com.xdsty.chatserver.attribute.Attributes;
import com.xdsty.chatserver.util.Constant;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * description 服务端空闲检测
 * @author 张富华
 * @version 1.0
 * @date 2019/11/8 13:49
 */
public class IMIdleStateHandler extends IdleStateHandler {

    private static final Logger log = LoggerFactory.getLogger(IMIdleStateHandler.class);

    private static final int READER_IDLE_TIME = 15;

    /**
     * times秒内未读取到客户端的数据，触发
     */
    public IMIdleStateHandler(){
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        String userId = ctx.channel().attr(Attributes.USERID).get();
        //从在线列表中移除当前用户 并关闭channel
        if(userId != null) {
            Constant.removeOnlineUser(userId);
            ctx.channel().close();
            log.info("用户: {}断开连接", userId);
        }
    }
}
