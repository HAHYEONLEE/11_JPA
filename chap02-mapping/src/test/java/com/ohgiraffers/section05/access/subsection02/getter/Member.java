package com.ohgiraffers.section05.access.subsection02.getter;

import javax.persistence.*;

@Entity(name="member_section05_subsection02")
@Table(name="tbl_member_section05_subsection02")
//@Access(AccessType.PROPERTY)  //클래스 레벨 : 모든 필드에 대해 getter 접근 방식 사용
// 주의할 점 : @Id 어노테이션이 필드에 잇으면 엔터티 생성이 안되므로 getter 메소드 위로 옮겨야함
public class Member {
    @Id
    @Column(name="member_no")
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

    //@Access(AccessType.PROPERTY)를 사용 할거면 @Id를 여기로 옮겨야 함
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


    @Access(AccessType.PROPERTY)  //메소드 레벨 : 해당 값의 접근 방식만 getter로 변경한다.
    public String getNickname() {
        System.out.println("getNickname()를 이용한 access 확인...");
        // 일반적으로는 필드 접근을 사용하지만 getter로 접근하면 가공, 확인등의 코드를 작성해서 사용할 수 있다.
        return nickname + "님";
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

