package com.xdsty.chatweb.service.impl;

import com.xdsty.chatweb.dao.UserDao;
import com.xdsty.chatweb.model.po.LoginUser;
import com.xdsty.chatweb.model.vo.UserInfo;
import com.xdsty.chatweb.service.UserService;
import com.xdsty.chatweb.util.Constant;
import com.xdsty.chatweb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * description
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/5 16:41
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Result checkLogin(LoginUser user, HttpSession session) {
        UserInfo userInfo = userDao.selectUserByUsernameAndPsd(user);
        if(userInfo != null){
            session.setAttribute(Constant.USER, userInfo);
            return Result.createSuccessResult();
        }
        return Result.createByFailure();
    }

    @Override
    public Result<UserInfo> getUserInfo(HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Constant.USER);
        return Result.createSuccessResult(userInfo);
    }
}
