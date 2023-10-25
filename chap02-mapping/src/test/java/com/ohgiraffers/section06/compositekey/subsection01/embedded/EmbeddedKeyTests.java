package com.ohgiraffers.section06.compositekey.subsection01.embedded;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmbeddedKeyTests {

    private static EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @BeforeAll //모든게 진행되기 전 딱 한 번 호출
    public static void initFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");  //persistence.xml에 persistence-unit name과 일치해야함
    }

    @BeforeEach // 테스트를 하기 전 마다 호출
    public void initManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterAll //테스트가 끝나고 딱 한 번 호출
    public static void closeFactory() {
        entityManagerFactory.close();
    }

    @AfterEach //테스트가 끝날 때 마다 호출
    public void closeManager() {
        entityManager.close();
    }

    @Test
    public void 임베디드_아이디_사용한_복합키_테이블_매핑_테스트() {
        //given
        Member member = new Member();
        member.setMemberPK(new MemberPK(1, "user01"));
        member.setPhone("010-1234-5678");
        member.setAddress("서울시 종로구");
        //when
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(member); //given의 비영속 객체를 영속 객체로 만든다.
        entityTransaction.commit();
        //then
        Member foundMember = entityManager.find(Member.class, member.getMemberPK());
        assertEquals(member.getMemberPK(), foundMember.getMemberPK());
    }
}
