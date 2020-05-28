package com.xdsty.chatweb.util;


import com.xdsty.chatweb.model.vo.GroupInfo;
import com.xdsty.chatweb.model.vo.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author 张富华
 * @version 1.0
 * @date 2019/11/4 17:41
 */
public class Constant {

    public static final String USER = "user";

    /**
     * 用户信息
     */
    public static Map<String, UserInfo> userInfoMap = new HashMap<>();

    /**
     * 只有一个分组
     */
    private static GroupInfo GROUP_INFO = new GroupInfo("01", "miku", "../img/avatar/miku.jpg");

    public static List<GroupInfo> GROUP_INFO_LIST;

    static {
        GROUP_INFO_LIST = new ArrayList<>();
        GROUP_INFO_LIST.add(GROUP_INFO);
    }

    public static final String [] IMGS = {"../img/avatar/miku.jpg"};

}
