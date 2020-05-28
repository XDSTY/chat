package com.xdsty.chatserver.util;

import com.alibaba.fastjson.JSONObject;

/**
 * description
 * @author 张富华
 * @version 1.0
 * @date 2019/11/5 9:12
 */
public class JsonUtil {

    public static JSONObject convertJson2Obj(String json){
        return JSONObject.parseObject(json);
    }

    public static String convertToJson(Object obj){
        return JSONObject.toJSONString(obj);
    }

}
