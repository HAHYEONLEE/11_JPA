package com.ohgiraffers.section01.simple;

import oracle.jdbc.internal.XSCacheOutput;
import org.junit.jupiter.api.*;

import javax.persistence.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SimpleJPQLTests {
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
    public void TypedQuery를_이용한_단일메뉴_조회_테스트() {
        //when
        String  jpql = "SELECT m.menuName FROM menu_section01 as m WHERE m.menuCode = 7";
        TypedQuery<String> query = entityManager.createQuery(jpql, String.class);  //createQuery(실행구문, 반환받을 데이터 타입) / query에는 반환데이터 타입이 들어감
        String resultMenuName = query.getSingleResult();
        //then
        assertEquals("민트미역국", resultMenuName);
    }

    @Test
    public void Query를_이용한_단일메뉴_조회_테스트() {
        //when
        String jpql = "SELECT m.menuName FROM menu_section01 as m WHERE m.menuCode = 7";
        Query query = entityManager.createQuery(jpql); // 결과 타입을 지정하지 않으면 Object 반환
        Object resultMenuName =  query.getSingleResult();
        //then
        assertEquals("민트미역국", resultMenuName);
    }

    @Test
    public void TypedQuery를_이용한_단일행_조회_테스트(){
        //when
        String jpql = "SELECT m FROM menu_section01 as m WHERE m.menuCode = 7"; //SELECT 뒤에 별칭만 써주면 전체를 조회한다.
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
        Menu foundMenu = query.getSingleResult();
        //then
        assertEquals(7, foundMenu.getMenuCode());
        System.out.println(foundMenu);
    }

    @Test
    public void TypedQuery를_이용한_다중행_조회_테스트() {
        //when
        String jpql = "SELECT m FROM menu_section01 as m"; //리스트로 조회하기 위해 as 뒤에 별칭만 붙임
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
        List<Menu> foundMenuList = query.getResultList();
        //then
        assertNotNull(foundMenuList);
        foundMenuList.forEach(System.out::println);
    }

    @Test
    public void Query를_이용한_다중행_조회_테스트() {
        //when
        String jpql = "SELECT m FROM menu_section01 as m"; //리스트로 조회하기 위해 as 뒤에 별칭만 붙임
        Query query = entityManager.createQuery(jpql);
        List<Menu> foundMenuList = query.getResultList();
        //then
        assertNotNull(foundMenuList);
        foundMenuList.forEach(System.out::println);
    }

    @Test
    public void distinct를_활용한_중복제거_여러_행_조회_테스트(){
        //when
        String jpql = "SELECT DISTINCT m.categoryCode FROM menu_section01 m";
        TypedQuery<Integer> query = entityManager.createQuery(jpql, Integer.class);
        List<Integer> categoryCodeList = query.getResultList();
        //then
        assertNotNull(categoryCodeList);
        categoryCodeList.forEach(System.out::println);
    }

    @Test
    public void in_연산자를_활용한_조회_테스트() {
        //카테고리 코드가 6이거나 10인 menu 조회
        String jpql = "SELECT m FROM menu_section01 as m WHERE m.categoryCode IN (6, 10)";
//        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
//        List<Menu> menuList = query.getResultList();
        /* 위의 두줄을 아래 한 줄로 변형 가능 */
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();
        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);

    }

    @Test
    public void like_연산자를_활용한_조회_테스트() {
        // "마늘"이 이름에 들어간 menu 조회
        String jpql = "SELECT m FROM menu_section01 as m WHERE m.menuName LIKE '%마늘%'";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();
        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }
}
