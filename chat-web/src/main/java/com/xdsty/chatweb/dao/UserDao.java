package com.xdsty.chatweb.dao;


import com.xdsty.chatweb.model.vo.UserInfo;

/**
 * description
 *
 * @author 张富华
 * @version 1.0
 * @date 2019/11/5 16:42
 */
public interface UserDao {

    UserInfo selectUserInfo(String username);

}
