package com.ohgiraffers.section04.onetoone.subsection02;

import javax.persistence.*;

@Entity(name = "user_info2")
@Table(name = "tbl_user_info2")
public class UserInfo {

    @Id
    private Long userInfoCode;
    private String nickName;
    private String phone;


    @OneToOne(mappedBy = "userInfo")
    private User user;

    public UserInfo() {
    }

    public UserInfo(Long userInfoCode, String nickName, String phone, User user) {
        this.userInfoCode = userInfoCode;
        this.nickName = nickName;
        this.phone = phone;
        this.user = user;
    }

    public Long getUserInfoCode() {
        return userInfoCode;
    }

    public void setUserInfoCode(Long userInfoCode) {
        this.userInfoCode = userInfoCode;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userInfoCode=" + userInfoCode +
                ", nickName='" + nickName + '\'' +
                ", phone='" + phone +
                '}';
    }
}
