package com.ohgiraffers.section01.simple;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NativeQueryTests {
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
    public void 결과_타입을_정의한_네이티브_쿼리_사용_테스트(){
        //given
        int menuCodeParameter = 15;
        //when
        String query = "SELECT MENU_CODE, MENU_NAME, MENU_PRICE, CATEGORY_CODE, ORDERABLE_STATUS" +
                " FROM TBL_MENU WHERE MENU_CODE = ?";
//        String query = "SELECT MENU_CODE, MENU_NAME, MENU_PRICE" +
//                " FROM TBL_MENU WHERE MENU_CODE = ?";  // 모든 항목을 다 작성 해야함.
        Query nativeQuery = entityManager.createNativeQuery(query, Menu.class)
                .setParameter(1, menuCodeParameter);
        Menu foundMenu = (Menu) nativeQuery.getSingleResult();
        //then
        assertNotNull(foundMenu);
        assertTrue(entityManager.contains(foundMenu));
        System.out.println(foundMenu);
    }

    @Test
    public void 결과_타입을_정의할_수_없는_경우_조회_테스트(){
        //when
        String query = "SELECT MENU_NAME, MENU_PRICE FROM TBL_MENU";
        List<Object[]> menuList = entityManager.createNativeQuery(query).getResultList();
        //then
        assertNotNull(menuList);
        menuList.forEach(row -> {
            Stream.of(row).forEach(col -> System.out.print(col + " "));
            System.out.println();
        });
    }

    @Test
    public void 자동_결과_매핑을_사용한_조회_테스트() {
        //when
        String query = "SELECT" +
                " A.CATEGORY_CODE, A.CATEGORY_NAME, A.REF_CATEGORY_CODE, NVL(V.MENU_COUNT, 0) MENU_COUNT" +
                " FROM TBL_CATEGORY A" +
                " LEFT JOIN (SELECT COUNT(*) AS MENU_COUNT, B.CATEGORY_CODE" +
                "            FROM TBL_MENU B" +
                "            GROUP BY B.CATEGORY_CODE) V ON (A.CATEGORY_CODE = V.CATEGORY_CODE)" +
                " ORDER BY 1";

        Query nativeQuery = entityManager.createNativeQuery(query, "categoryCountAutoMapping");
        List<Object[]> categoryList = nativeQuery.getResultList();
        assertNotNull(categoryList);
        assertTrue(entityManager.contains(categoryList.get(0)[0]));
        categoryList.forEach(row -> {
            Stream.of(row).forEach(col -> System.out.print(col + " "));
            System.out.println();
        });
    }

    @Test
    public void 수동_결과_매핑을_사용한_조회_테스트() {
        //when
        String query = "SELECT" +
                " A.CATEGORY_CODE, A.CATEGORY_NAME, A.REF_CATEGORY_CODE, NVL(V.MENU_COUNT, 0) MENU_COUNT" +
                " FROM TBL_CATEGORY A" +
                " LEFT JOIN (SELECT COUNT(*) AS MENU_COUNT, B.CATEGORY_CODE" +
                "            FROM TBL_MENU B" +
                "            GROUP BY B.CATEGORY_CODE) V ON (A.CATEGORY_CODE = V.CATEGORY_CODE)" +
                " ORDER BY 1";
        Query nativeQuery = entityManager.createNativeQuery(query, "categoryCountManualMapping");
        List<Object[]> categoryList = nativeQuery.getResultList();
        assertNotNull(categoryList);
        assertTrue(entityManager.contains(categoryList.get(0)[0]));
        categoryList.forEach(row -> {
            Stream.of(row).forEach(col -> System.out.print(col + " "));
            System.out.println();
        });
    }
}
