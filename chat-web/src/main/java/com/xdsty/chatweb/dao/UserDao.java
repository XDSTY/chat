package com.xdsty.chatweb.dao;


import com.xdsty.chatweb.model.po.LoginUser;
import com.xdsty.chatweb.model.vo.UserInfo;

/**
 * description
 *
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/5 16:42
 */
public interface UserDao {

    UserInfo selectUserInfo(String username);

    UserInfo selectUserByUsernameAndPsd(LoginUser user);

}
