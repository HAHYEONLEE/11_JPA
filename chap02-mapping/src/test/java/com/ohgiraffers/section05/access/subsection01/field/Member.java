package com.ohgiraffers.section05.access.subsection01.field;

import javax.persistence.*;
import java.util.Date;

@Entity(name="member_section05_subsection01")
@Table(name="tbl_member_section05_subsection01")
@Access(AccessType.FIELD)   // 클래스 레벨 : 모든 필드에 대해 필드 접근 방식 사용
public class Member {
    @Id
    @Column(name="member_no")
    @Access(AccessType.FIELD)  // 필드 레벨 : 해당 필드에 대해 필드 접근 방식 사용
    private int memberNo;
    @Column(name="member_id")
    private String memberId;
    @Column(name="member_pwd")
    private String memberPwd;
    @Column(name="nickname")
    private String nickname;

    public Member() {}

    public Member(int memberNo, String memberId, String memberPwd, String nickname) {
        super();
        this.memberNo = memberNo;
        this.memberId = memberId;
        this.memberPwd = memberPwd;
        this.nickname = nickname;
    }

    public int getMemberNo() {
        System.out.println("getMemberNo()를 이용한 access 확인...");
        return memberNo;
    }

    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }

    public String getMemberId() {
        System.out.println("getMemberId()를 이용한 access 확인...");
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberPwd() {
        System.out.println("getMemberPwd()를 이용한 access 확인...");
        return memberPwd;
    }

    public void setMemberPwd(String memberPwd) {
        this.memberPwd = memberPwd;
    }

    public String getNickname() {
        System.out.println("getNickname()를 이용한 access 확인...");
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberNo=" + memberNo +
                ", memberId='" + memberId + '\'' +
                ", memberPwd='" + memberPwd + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}

