package com.xdsty.chatserver.msg;

import com.xdsty.chatserver.util.JsonUtil;
import lombok.Data;

/**
 * description
 *
 * @author 张富华
 * @version 1.0
 * @date 2019/11/5 10:37
 */
@Data
public class ErrMsg {

    private String type = MsgType.ERR_MSG;

    private String code;

    private String msg;

    public static ErrMsg build(){
        return new ErrMsg();
    }

    public ErrMsg setCode(String code) {
        this.code = code;
        return this;
    }

    public ErrMsg setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    @Override
    public String toString() {
        return JsonUtil.convertToJson(this);
    }
}
