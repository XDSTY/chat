package com.xdsty.chatserver.util;

import com.xdsty.chatserver.model.vo.UserInfo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * description
 *
 * @author 张富华
 * @version 1.0
 * @date 2019/11/5 11:05
 */
@Component
public class FakeData {

    private static Map<String, UserInfo> userMap = new HashMap<>(16);

}
