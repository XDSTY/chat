package com.xdsty.chatweb.controller;

import com.xdsty.chatweb.model.po.LoginUser;
import com.xdsty.chatweb.service.UserService;
import com.xdsty.chatweb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * description
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/4 10:21
 */
@RestController
@RequestMapping("user")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public Result login(@RequestBody LoginUser user, HttpServletRequest request){
        return userService.checkLogin(user, request.getSession());
    }

}
