package com.xdsty.chatweb.controller;

import com.xdsty.chatweb.disaptch.ConsistentHash;
import com.xdsty.chatweb.model.vo.UserInfo;
import com.xdsty.chatweb.service.UserService;
import com.xdsty.chatweb.util.Constant;
import com.xdsty.chatweb.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * description
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/5 16:39
 */
@RestController
@RequestMapping("user")
public class UserInfoController {

    private static final Logger log = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private UserService userService;

    /**
     * 获取用户个人信息
     */
    @GetMapping("info")
    public Result<UserInfo> getUserInfo(HttpSession session){
        return userService.getUserInfo(session);
    }

    /**
     * 根据一致性hash获取一个有效的websocket server
     */
    @GetMapping("host")
    public Result<String> getRoutingPath(HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute(Constant.USER);
        String host = null;
        try {
            host = ConsistentHash.getServer(userInfo.getUserId());
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Result.createByFailure();
        }
        log.info("用户{} 连接到 {}", userInfo.getUserId(), host);
        return Result.createSuccessResult(host);
    }

}
