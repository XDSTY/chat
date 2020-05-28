package com.xdsty.chatserver.attribute;

import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.util.AttributeKey;

/**
 * description
 *
 * @author 张富华
 * @version 1.0
 * @date 2019/11/5 9:32
 */
public interface Attributes {

    AttributeKey<WebSocketServerHandshaker> HANDSHAKER = AttributeKey.newInstance("handshaker");

    AttributeKey<String> USERID = AttributeKey.newInstance("userId");
}
