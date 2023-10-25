package com.ohgiraffers.section04.onetoone.subsection02;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OneToOneAssociationTests {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    @BeforeAll
    public static void initFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
    }
    @BeforeEach
    public void initManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }
    @AfterAll
    public static void closeFactory() {
        entityManagerFactory.close();
    }
    @AfterEach
    public void closeManager() {
        entityManager.close();
    }

    /* 외래키가 user_info에 있는 경우 단방향 연관 관계는 JAP에서 지원하지 않는다. */
    @Test
    public void 외래키가_user_info에_있는_경우_양방향_연관관계_테스트() {
        //given
        Long userCode = 1L;
        //when
        User user = entityManager.find(User.class, userCode);
        //then
        assertNotNull(user);
        System.out.println(user);
        System.out.println(user.getUserInfo());
        System.out.println(user.getUserInfo().getUser());
    }
}


