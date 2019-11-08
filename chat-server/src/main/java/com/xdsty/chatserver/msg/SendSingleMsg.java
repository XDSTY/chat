package com.xdsty.chatserver.msg;

import com.xdsty.chatserver.util.JsonUtil;
import lombok.Data;

/**
 * description
 *
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/5 10:31
 */
@Data
public class SendSingleMsg {

    private String fromUserId;

    private String content;

    private String type = MsgType.SINGLE_MSG;

    public static SendSingleMsg build(){
        return new SendSingleMsg();
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public SendSingleMsg setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public SendSingleMsg setContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    public String toString() {
        return JsonUtil.convertToJson(this);
    }
}
