package com.xdsty.chatserver.msg;

/**
 * description
 *
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/5 10:34
 */
public interface MsgType {

    /**
     * 错误消息
     */
    String ERR_MSG = "ERR";

    /**
     * 点对点普通消息
     */
    String SINGLE_MSG = "SINGLE_SENDING";

    /**
     * 点对点binary消息
     */
    String SINGLE_BINARY = "SINGLE_BIN";

    /**
     * 注册消息
     */
    String REGISTER = "REGISTER";

    /**
     * 心跳包
     */
    String HEART_BEAT = "HEART_BEAT";

}
