package com.xdsty.chatweb.model.vo;


import lombok.Data;

import java.util.List;

/**
 * description
 *
 * @author 张富华
 * @version 1.0
 * @date 2019/11/5 16:12
 */
@Data
public class UserInfo {

    private String userId;

    private String username;

    private String profileImg;

    private List<GroupInfo> groupList;

    public UserInfo(String userId, String username, String profileImg) {
        this.userId = userId;
        this.username = username;
        this.profileImg = profileImg;
    }

}
