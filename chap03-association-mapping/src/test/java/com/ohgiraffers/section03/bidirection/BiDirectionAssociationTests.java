package com.ohgiraffers.section03.bidirection;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BiDirectionAssociationTests {
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
    public void 양방향_연관관계_매핑_조회_테스트() {
        //given
        int menuCode = 10;
        int categoryCode = 10;
        //when
        /* 진짜 연관 관계는 최초 조회 시 조인 결과를 인출한다. */
        Menu foundMenu = entityManager.find(Menu.class, menuCode);
        /* 가짜 연관 관계는 Category 엔티티만 조회하고 필요 시 연 관계 엔티티를 조회하는 쿼리를 다시 실행하게 된다. */
        Category foundCategory = entityManager.find(Category.class, categoryCode);
        //then
        assertEquals(menuCode, foundMenu.getMenuCode());
        assertEquals(categoryCode, foundCategory.getCategoryCode());

        /* 주의사항
        * toString 오버라이딩 시 양방향 연관 관계는 재귀 호출이 일어나기 때문에 StackOverflowError가 발생한다.
        * 엔터티의 주인이 아닌 쪽의 toString에서 연관 객체 부분이 출력되지 않도록 삭제한다.
        * 특히 자동 완성 or 롬복 라이브러리 사용 시 주의한다. */
        System.out.println(foundMenu);
        System.out.println(foundCategory);
        /* 메뉴 리스트가 필요한 순간 다시 조회 쿼리가 동작하며 참조 된다. */
        foundCategory.getMenuList().forEach(System.out::println);
        /* 양방향 참조시 메뉴 -> 카테고리 -> 메뉴리스트 참조도 가능하다.*/
        foundMenu.getCategory().getMenuList().forEach(System.out::println);
    }

    @Test
    public void 양방향_연관관계_주인_객체를_이용한_삽입_테스트() {
        //given
        Menu menu = new Menu();
        menu.setMenuCode(2222);
        menu.setMenuName("연관관계주인메뉴");
        menu.setMenuPrice(10000);
        menu.setOrderableStatus("Y");
        menu.setCategory(entityManager.find(Category.class, 4));
        //when
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(menu);
        entityTransaction.commit();
        //then
        Menu foundMenu = entityManager.find(Menu.class, menu.getMenuCode());
        assertEquals(menu.getMenuCode(), foundMenu.getMenuCode());
        System.out.println(foundMenu);
    }

    @Test
    public void 양방향_연관관계_주인이_아닌_객체를_이용한_삽입_테스트() {
        //given
        Category category = new Category();
        category.setCategoryCode(1004);
        category.setCategoryName("양방향카테고리");
        category.setRefCategoryCode(1);
        //when
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(category);
        entityTransaction.commit();
        //then
        Category foundCategory = entityManager.find(Category.class, category.getCategoryCode());
        assertEquals(category.getRefCategoryCode(), foundCategory.getRefCategoryCode());
        System.out.println(foundCategory);
    }
}
