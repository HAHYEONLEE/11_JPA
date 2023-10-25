package com.ohgiraffers.section04.onetoone.subsection03;

import javax.persistence.*;

@Entity(name = "user3")
@Table(name = "tbl_user3")
public class User {

    @Id
    private Long userCode;
    private String userId;
    private String userPwd;

    @OneToOne(mappedBy = "user")
    private UserInfo userInfo;

    public User() {
    }

    public User(Long userCode, String userId, String userPwd, UserInfo userInfo) {
        this.userCode = userCode;
        this.userId = userId;
        this.userPwd = userPwd;
        this.userInfo = userInfo;
    }

    public Long getUserCode() {
        return userCode;
    }

    public void setUserCode(Long userCode) {
        this.userCode = userCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "User{" +
                "userCode=" + userCode +
                ", userId='" + userId + '\'' +
                ", userPwd='" + userPwd +
                '}';
    }
}
