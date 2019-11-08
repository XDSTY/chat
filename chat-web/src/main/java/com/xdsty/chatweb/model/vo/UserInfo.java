package com.xdsty.chatweb.model.vo;

import java.util.List;

/**
 * description
 *
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/5 16:12
 */
public class UserInfo {

    private String userId;

    private String username;

    private String password;

    private String profileImg;

    private List<UserInfo> friendList;

    private List<GroupInfo> groupList;

    public UserInfo(String userId, String username, String password, String profileImg) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.profileImg = profileImg;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public List<UserInfo> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<UserInfo> friendList) {
        this.friendList = friendList;
    }

    public List<GroupInfo> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<GroupInfo> groupList) {
        this.groupList = groupList;
    }
}
