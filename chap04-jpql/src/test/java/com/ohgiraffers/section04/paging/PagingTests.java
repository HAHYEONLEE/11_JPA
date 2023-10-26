package com.ohgiraffers.section04.paging;


import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PagingTests {
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

    @Test
public void 페이징_API를_이용한_조회_테스트() {
        //given
        int offset = 10;  //뛰어 넘고 싶은 행의 갯수  11부터 보고싶다는 뜻
        int limit = 5;  //최대 몇개 가져올거냐 11~15까지라는 뜻
        //when
        String jpql = "SELECT m FROM menu_section04 m ORDER BY m.menuCode DESC";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class)
                .setFirstResult(offset)  //첫번째 결과 -> 조회를 시작할 위치
                .setMaxResults(limit)    // 최대 몇개?
                .getResultList();
        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }
}
