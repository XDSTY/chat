package com.xdsty.chatserver.msg;

import lombok.Data;

/**
 * description
 *
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/5 20:45
 */
@Data
public class RegisterMsg {

    private String type = MsgType.REGISTER;
}
