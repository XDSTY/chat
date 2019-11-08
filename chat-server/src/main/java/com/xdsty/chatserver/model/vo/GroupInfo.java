package com.xdsty.chatserver.model.vo;

import java.util.List;

/**
 * description
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/5 16:14
 */
public class GroupInfo {

    private String groupId;

    private String groupName;

    private String groupImg;

    private List<UserInfo> members;

    public GroupInfo(String groupId, String groupName, String groupImg) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupImg = groupImg;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupImg() {
        return groupImg;
    }

    public void setGroupImg(String groupImg) {
        this.groupImg = groupImg;
    }

    public List<UserInfo> getMembers() {
        return members;
    }

    public void setMembers(List<UserInfo> members) {
        this.members = members;
    }
}
