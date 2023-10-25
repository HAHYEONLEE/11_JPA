package com.ohgiraffers.section02.column;

import javax.persistence.*;
import java.util.Date;

@Entity(name="member_section02")    //Entity와 Table 모두 다른 클래스와 이름 겹치지 않게 구분하기
@Table(name="tbl_member_section02")
public class Member {
    @Id
    @Column(name="member_no")
    private int memberNo;
    @Column(name="member_id", length = 20)
    private String memberId;
    @Column(name="member_pwd")
    private String memberPwd;
    @Column(name="nickname")
    @Transient    // 테이블로 만들 때 이 컬럼은 만들지 않겠다(요소를 사용하지 않겠다)
    private String nickname;
    @Column(name="phone", columnDefinition = "varchar2(200) default '010-0000-0000'")  //columnDefinition 속성을 직접 입력할 때
    private String phone;
    @Column(name="email", unique = true)  //email은 고유 값이어야만 한다.
    private String email;
    @Column(name="address", nullable = false)
    private String address;
    @Column(name="enroll_date")
    @Temporal(TemporalType.TIMESTAMP)   // DATE + TIME : 날짜 및 시간
    //@Temporal(TemporalType.DATE)        // 오라클에서는 TIMESTAMP와 동일
    //@Temporal(TemporalType.TIME)        //1970/01/01에 시간 맞게 나옴(시간만 취급)
    private Date enrollDate;
    @Column(name="member_role")
    private String memberRole;
    @Column(name="status")
    private String status;
    public Member() {}
    public Member(int memberNo, String memberId, String memberPwd, String nickname, String phone,
                  String email, String address, Date enrollDate, String memberRole, String status) {
        super();
        this.memberNo = memberNo;
        this.memberId = memberId;
        this.memberPwd = memberPwd;
        this.nickname = nickname;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.enrollDate = enrollDate;
        this.memberRole = memberRole;
        this.status = status;
    }
    public int getMemberNo() {
        return memberNo;
    }
    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }
    public String getMemberId() {
        return memberId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    public String getMemberPwd() {
        return memberPwd;
    }
    public void setMemberPwd(String memberPwd) {
        this.memberPwd = memberPwd;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Date getEnrollDate() {
        return enrollDate;
    }
    public void setEnrollDate(Date enrollDate) {
        this.enrollDate = enrollDate;
    }
    public String getMemberRole() {
        return memberRole;
    }
    public void setMemberRole(String memberRole) {
        this.memberRole = memberRole;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "Member [memberNo=" + memberNo + ", memberId=" + memberId + ", memberPwd=" + memberPwd
                + ", nickname=" + nickname + ", phone=" + phone + ", email=" + email
                + ", address=" + address + ", enrollDate=" + enrollDate + ", memberRole=" + memberRole
                + ", status=" + status + "]";
    }
}

