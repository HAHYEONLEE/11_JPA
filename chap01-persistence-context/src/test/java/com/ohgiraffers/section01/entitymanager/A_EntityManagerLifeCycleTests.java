package com.ohgiraffers.section01.entitymanager;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class A_EntityManagerLifeCycleTests {

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

    @Test //테스트 시 사용되는 어노테이션
    public void 엔터티_매니저_팩토리와_엔터티_매니저_생명주기_확인1() {
        System.out.println("entityMAnagerFactory.hashCode : " + entityManagerFactory.hashCode());
        System.out.println("entityManager.hashCode : " + entityManager.hashCode());
    }

    @Test
    public void 엔터티_매니저_팩토리와_엔터티_매니저_생명주기_확인2() {
        System.out.println("entityMAnagerFactory.hashCode : " + entityManagerFactory.hashCode());
        System.out.println("entityManager.hashCode : " + entityManager.hashCode());
    }
}
