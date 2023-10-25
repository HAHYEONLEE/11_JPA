package com.ohgiraffers.section01.manytoone;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ManyToOneAssociationTests {

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
    public void 다대일_연관관계_객체_그래프_탐색을_이용한_조회_테스트() {
        //given
        int menuCode = 15;
        //when
        //조회 시 조인 구문이 실행 되며 연관 테이블을 함께 조회 해온다.
        MenuAndCategory foundMenu = entityManager.find(MenuAndCategory.class, menuCode);
        Category menuCategory = foundMenu.getCategory();
        //then
        assertNotNull(menuCategory);
        System.out.println("menuCategory = " + menuCategory);
    }

    @Test
    public void 다대일_연관관계_객체지향쿼리_사용한_카테고리_이름_조회_테스트() {
        //given
        String jpql = "SELECT c.categoryName FROM menu_and_category m JOIN m.category c WHERE m.menuCode = 15";

        //조회시 조인 구문이 함께 실행되며 연관 테이블을 함께 조회해온다.
        //when
        String categoryName = entityManager.createQuery(jpql, String.class).getSingleResult();

        //then
        assertNotNull(categoryName);
        System.out.println("categoryName = " + categoryName);
    }

    @Test
    public void 다대일_연관관계_객체_삽입_테스트() {
        //given
        MenuAndCategory menuAndCategory = new MenuAndCategory();
        menuAndCategory.setMenuCode(9999);
        menuAndCategory.setMenuName("죽방멸치빙수");
        menuAndCategory.setMenuPrice(30000);
        Category category = new Category();
        category.setCategoryCode(444);
        category.setCategoryName("신규카테고리");
        category.setRefCategoryCode(1);
        menuAndCategory.setCategory(category);
        menuAndCategory.setOrderableStatus("Y");
        //when
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(menuAndCategory);
        entityTransaction.commit();
        //then
        MenuAndCategory foundMenuAndCategory = entityManager.find(MenuAndCategory.class, 9999);
        assertEquals(9999, foundMenuAndCategory.getMenuCode());
        assertEquals(444, foundMenuAndCategory.getCategory().getCategoryCode());
    }
}
