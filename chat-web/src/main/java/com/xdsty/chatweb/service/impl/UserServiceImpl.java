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
import java.util.Random;
import java.util.UUID;

/**
 * description
 * @author 张富华
 * @version 1.0
 * @date 2019/11/5 16:41
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 生成随机用户
     */
    @Override
    public Result register(LoginUser user, HttpSession session) {
        String profileImg = generateProfileImg();
        String userId = generateUserId();
        UserInfo userInfo = new UserInfo(userId, user.getUsername(), profileImg);
        userInfo.setGroupList(Constant.GROUP_INFO_LIST);
        session.setAttribute(Constant.USER, userInfo);
        return Result.createSuccessResult();
    }

    private String generateUserId(){
        return UUID.randomUUID().toString();
    }

    private String generateProfileImg(){
        Random random = new Random();
        return Constant.IMGS[random.nextInt(Constant.IMGS.length)];
    }

    @Override
    public Result<UserInfo> getUserInfo(HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Constant.USER);
        return Result.createSuccessResult(userInfo);
    }
}
