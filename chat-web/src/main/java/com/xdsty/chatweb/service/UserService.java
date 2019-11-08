package com.xdsty.chatweb.service;


import com.xdsty.chatweb.model.po.LoginUser;
import com.xdsty.chatweb.model.vo.UserInfo;
import com.xdsty.chatweb.util.Result;

import javax.servlet.http.HttpSession;

/**
 * description
 *
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/5 16:40
 */
public interface UserService {

    Result checkLogin(LoginUser user, HttpSession session);

    Result<UserInfo> getUserInfo(HttpSession session);

}
