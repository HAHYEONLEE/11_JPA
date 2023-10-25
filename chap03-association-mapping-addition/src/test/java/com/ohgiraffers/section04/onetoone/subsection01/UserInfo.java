package com.ohgiraffers.section04.onetoone.subsection01;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "user_info1")
@Table(name = "tbl_user_info1")
public class UserInfo {

    @Id
    private Long userInfoCode;
    private String nickName;
    private String phone;

    public UserInfo() {
    }

    public UserInfo(Long userInfoCode, String nickName, String phone) {
        this.userInfoCode = userInfoCode;
        this.nickName = nickName;
        this.phone = phone;
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

    @Override
    public String toString() {
        return "UserInfo{" +
                "userInfoCode=" + userInfoCode +
                ", nickName='" + nickName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
