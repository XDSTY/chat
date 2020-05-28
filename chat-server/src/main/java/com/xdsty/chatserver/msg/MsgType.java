package com.xdsty.chatserver.msg;

/**
 * description
 *
 * @author 张富华
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
     * 点对点图片消息消息
     */
    String SINGLE_IMG = "SINGLE_IMG";

    /**
     * 注册消息
     */
    String REGISTER = "REGISTER";

    /**
     * 心跳包
     */
    String HEART_BEAT = "HEART_BEAT";

}
