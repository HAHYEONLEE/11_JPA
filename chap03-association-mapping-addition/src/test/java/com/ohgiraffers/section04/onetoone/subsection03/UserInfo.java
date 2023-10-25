package com.ohgiraffers.section04.onetoone.subsection03;

import javax.persistence.*;

@Entity(name = "user_info3")
@Table(name = "tbl_user_info3")
public class UserInfo {

    @Id
    private Long userCode; //PFK로 User 테이블의 PK명과 일치시킨다.
    private String nickName;
    private String phone;

    @OneToOne
    @JoinColumn(name = "userCode")
    private User user;

    public UserInfo() {
    }

    public UserInfo(Long userCode, String nickName, String phone, User user) {
        this.userCode = userCode;
        this.nickName = nickName;
        this.phone = phone;
        this.user = user;
    }

    public Long getUserCode() {
        return userCode;
    }

    public void setUserCode(Long userCode) {
        this.userCode = userCode;
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
                "userCode=" + userCode +
                ", nickName='" + nickName + '\'' +
                ", phone='" + phone + '\'' +
                ", user=" + user +
                '}';
    }
}
