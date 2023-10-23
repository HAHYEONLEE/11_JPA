package com.ohgiraffers.section02.crud;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class A_EntityManagerCRUDTests {

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
    public void 메뉴코드로_메뉴_조회_테스트() {
        //given(테스트를 하기 위해서 주어진 것)
        int menuCode = 2;
        //when
        Menu foundMenu = entityManager.find(Menu.class, menuCode);
        //then
        Assertions.assertNotNull(foundMenu);
        Assertions.assertEquals(menuCode, foundMenu.getMenuCode());
        System.out.println("foundMenu = " + foundMenu);
    }

    @Test
    public void 새로운_메뉴_추가_테스트() {
        //given
        Menu menu = new Menu();
        menu.setMenuName("JPA 테스트용 신규 메뉴");
        menu.setMenuPrice(5000);
        menu.setCategoryCode(4);
        menu.setOrderableStatus("Y");
        //when
        EntityTransaction entityTransaction = entityManager.getTransaction();  //entityManager로 부터 Transaction을 가져와서
        entityTransaction.begin();  //Transaction을 시작하고
        try {
            entityManager.persist(menu); //저장한 다음
            entityTransaction.commit();  //Transaction에 커밋하겠다.(insert)
        } catch (Exception e){
            entityTransaction.rollback();  //exception이 발생하면 명시적으로 rollback 해준다.
            e.printStackTrace();
        }
        //then
        Assertions.assertTrue(entityManager.contains(menu));
    }

    @Test
    public void 메뉴_이름_수정_테스트() {
        //given
        Menu menu = entityManager.find(Menu.class, 2);
        System.out.println("menu : " + menu);
        String menuNameToChange = "갈치스무디";
        //when
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        try {
            menu.setMenuName(menuNameToChange);
            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        }
        //then
        Assertions.assertEquals(menuNameToChange, entityManager.find(Menu.class, 2).getMenuName());

    }

    @Test
    public void 메뉴_삭제하기_테스트() {
        //given
        Menu menuToRemove = entityManager.find(Menu.class, 1);
        //when
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        try {
            entityManager.remove(menuToRemove);
            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        }
        //then
        Menu removedMenu = entityManager.find(Menu.class, 1);
        Assertions.assertEquals(null, removedMenu); //1이 삭제되면 null이 된다고 예상하는 구문
    }
}
