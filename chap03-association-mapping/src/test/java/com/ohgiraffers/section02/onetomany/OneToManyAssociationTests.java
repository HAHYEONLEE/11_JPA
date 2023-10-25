package com.ohgiraffers.section02.onetomany;

import com.ohgiraffers.section01.manytoone.Category;
import org.junit.jupiter.api.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OneToManyAssociationTests {

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
    public void 일대다_연관관계_객체_그래프_탐색을_이용한_조회_테스트() {
        //given
        int categoryCode = 10;
        //when
        //일대다 연관관계의 경우 해당(category) 테이블만 조회하고 연관 된 menu 테이블은 아직 조회하지 않는다.
        CategoryAndMenu categoryAndMenu = entityManager.find(CategoryAndMenu.class, categoryCode);
        //then
        assertNotNull(categoryAndMenu);
        //해당 데이터가 사용되는 경우 연관된 menu 테이블을 조회하는 구문이 실행된다.
        System.out.println(categoryAndMenu);


    }

    @Test
    public void 일대다_연관관계_객체_삽입_테스트() {
        //given
        CategoryAndMenu categoryAndMenu = new CategoryAndMenu();
        categoryAndMenu.setCategoryCode(777);
        categoryAndMenu.setCategoryName("일대다추가카테고리");
        categoryAndMenu.setRefCategoryCode(1);

        List<Menu> menuList = new ArrayList<>();
        Menu menu = new Menu();
        menu.setMenuCode(777);
        menu.setMenuName("일대다아이스크림");
        menu.setMenuPrice(50000);
        menu.setCategoryCode(categoryAndMenu.getCategoryCode());
        menu.setOrderableStatus("Y");

        menuList.add(menu);

        categoryAndMenu.setMenuList(menuList);

        //when
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(categoryAndMenu);
        entityTransaction.commit();

        //then
        CategoryAndMenu foundCategoryAndMenu = entityManager.find(CategoryAndMenu.class, 777);
        System.out.println("foundCategoryAndMenu = " + foundCategoryAndMenu);
    }
}
