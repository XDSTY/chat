package com.xdsty.chatweb.dao.impl;

import com.xdsty.chatweb.dao.UserDao;
import com.xdsty.chatweb.model.vo.UserInfo;
import org.springframework.stereotype.Component;


/**
 * description
 * @author 张富华
 * @version 1.0
 * @date 2019/11/5 16:42
 */
@Component
public class UserDaoImpl implements UserDao {

    @Override
    public UserInfo selectUserInfo(String username) {
        return null;
    }
}
