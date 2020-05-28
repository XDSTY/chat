package com.xdsty.chatserver.msg;

import lombok.Data;

/**
 * description
 *
 * @author 张富华
 * @version 1.0
 * @date 2019/11/8 14:33
 */
@Data
public class HeartBeatMsg {

    private String type = MsgType.HEART_BEAT;

}
