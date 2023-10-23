package com.ohgiraffers.section03.persistencecontext;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.*;

public class A_EntityLifeCycleTests {

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
    public void 비영속성_테스트() {
        //given
        Menu foundMenu = entityManager.find(Menu.class, 11);  //foundMenu는 영속 엔티티
        Menu newMenu = new Menu();   //newMenu는 비영속성 (영속이랑 관계 없이 방금 만들어졌기 때문)
        newMenu.setMenuCode(foundMenu.getMenuCode());
        newMenu.setMenuName(foundMenu.getMenuName());
        newMenu.setMenuPrice(foundMenu.getMenuPrice());
        newMenu.setCategoryCode(foundMenu.getCategoryCode());
        newMenu.setOrderableStatus(foundMenu.getOrderableStatus());
        //when
        boolean isTrue = (foundMenu == newMenu);    //false 주소값은 다르기 때문에
        //then
        assertFalse(isTrue);
    }

    @Test
    public void 영속성_연속_조회_테스트() {
        //given
        Menu foundMenu1 = entityManager.find(Menu.class, 11); //select해서 만들어서 반환
        // find의 내부적인 동작 :1차캐시 확인 -> 없음 -> select구문 실행 -> 엔터티 객체 생성 및 매핑
        Menu foundMenu2 = entityManager.find(Menu.class, 11); //1차캐시 반환(만들어진걸 찾아감)
        // 여기서의 find는 1차캐시 확인 -> 있음 -> 반환
        //when
        boolean isTrue = (foundMenu1 == foundMenu2);  //true 동일한 주소값
        //then
        assertTrue(isTrue);
    }

    @Test
    public void 영속성_객체_추가_테스트() {
        //given
        Menu menuToRegist = new Menu();
        menuToRegist.setMenuCode(500);
        menuToRegist.setMenuName("수박죽");
        menuToRegist.setMenuPrice(10000);
        menuToRegist.setCategoryCode(1);
        menuToRegist.setOrderableStatus("Y");
        //when
        entityManager.persist(menuToRegist); // 위의 내용을 insert함(1차캐시가 생성 됨)
        Menu foundMenu = entityManager.find(Menu.class, 500); // find가 1차캐시를 확인할 때 찾을 수 있음
        boolean isTrue = (menuToRegist == foundMenu);  //true 위의 내용으로 주소 값이 같음
        //then
        assertTrue(isTrue);
    }

    @Test
    public void 영속석_객체_추가_값_변경_테스트() {
        //given
        Menu menuToRegist = new Menu();
        menuToRegist.setMenuCode(500);
        menuToRegist.setMenuName("수박죽");
        menuToRegist.setMenuPrice(10000);
        menuToRegist.setCategoryCode(1);
        menuToRegist.setOrderableStatus("Y");
        //when
        entityManager.persist(menuToRegist);
        menuToRegist.setMenuName("메론죽");
        Menu foundMenu = entityManager.find(Menu.class, 500);
        //then
        assertEquals("메론죽", foundMenu.getMenuName());
    }

    @Test
    public void 준영속성_detach_테스트() {
        //given
        Menu foundMenu1 = entityManager.find(Menu.class, 11);
        Menu foundMenu2 = entityManager.find(Menu.class, 12);
        //when
        entityManager.detach(foundMenu2);  //detach = 1차캐시(영속성 context에서 제거가 된다.(영속 상태가 아니게 된다.)
        foundMenu1.setMenuPrice(5000);
        foundMenu2.setMenuPrice(5000);
        //then
        assertEquals(5000,entityManager.find(Menu.class, 11).getMenuPrice());  //true
        assertEquals(5000,entityManager.find(Menu.class, 12).getMenuPrice());  //false
        // 비영속성 상태로 돌아갔기 때문에 DB에서 원래의 값을 찾아서 새로운 캐시를 생성하므로 주소값이 같지 않다.
    }

    @Test
    public void 준영속성_clear_테스트() {
        //given
        Menu foundMenu1 = entityManager.find(Menu.class, 11);
        Menu foundMenu2 = entityManager.find(Menu.class, 12);
        //when
        entityManager.clear();  //entityManager가 관리하는 context를 clear하겠다. 객체는 존재하지만 영속성 context에서는 관리되지 않는다.
        foundMenu1.setMenuPrice(5000);
        foundMenu2.setMenuPrice(5000);
        //then
        assertEquals(5000,entityManager.find(Menu.class, 11).getMenuPrice()); //false
        assertEquals(5000,entityManager.find(Menu.class, 12).getMenuPrice()); //false
        // 1번에서 이미 오류가 발생했기 때문에 2번까지 진행되지 않는다.
    }

    @Test
    public void 준영속성_close_테스트() {
        //given
        Menu foundMenu1 = entityManager.find(Menu.class, 11);
        Menu foundMenu2 = entityManager.find(Menu.class, 12);
        //when
        entityManager.close();  //entityManager를 닫아버렸기 때문에 아래에서 find를 진행할 수 없다.(모든 동작 안됨)
        foundMenu1.setMenuPrice(5000);
        foundMenu2.setMenuPrice(5000);
        //then
        assertEquals(5000,entityManager.find(Menu.class, 11).getMenuPrice());  //false
        assertEquals(5000,entityManager.find(Menu.class, 12).getMenuPrice());  //false
    }

    @Test
    public void 삭제_remove_테스트() {
        //given
        Menu foundMenu = entityManager.find(Menu.class, 2);
        //when
        entityManager.remove(foundMenu); // 삭제(영속성 context에서는 찾을 수 없음)했기 때문에 DB에서 다시 찾지 않는다.
        Menu refoundMenu = entityManager.find(Menu.class, 2); // 그렇기 때문에 refoundMenu = null
        //then
        assertEquals(2, foundMenu.getMenuCode());
        assertEquals(null, refoundMenu);
    }

    @Test
    public void 병합_merge_수정_테스트() {  //merge시에는 PK값을 본다.
        //given
        Menu menuToDetach = entityManager.find(Menu.class, 2);// 조회: 갈치스무디 조회됨.
        entityManager.detach(menuToDetach); // 1차캐시 삭제
        //when
        menuToDetach.setMenuName("수박죽"); // 객체가 수박죽으로 바뀜(기존 주소)
        Menu refoundMenu = entityManager.find(Menu.class, 2); // 2번을 새로 조회 : 주소값은 다름
        entityManager.merge(menuToDetach); // menuToDetach인 수박을 관리되고 있는 새로 조회된 값의 캐시에 merge
        //then
        Menu mergedMenu = entityManager.find(Menu.class, 2);
        assertEquals("수박죽", mergedMenu.getMenuName()); // 따라서 수박죽이 됨.
    }

    @Test
    public void 병합_merge_삽입_테스트() {  //merge시에  PK값이 일치하지 않으면 데이터는 insert 된다.
        //given
        Menu menuToDetach = entityManager.find(Menu.class, 2);  //2번 조회
        entityManager.detach(menuToDetach); // 준영속 상태로 변경
        //when
        menuToDetach.setMenuCode(999); //DB에서 조회할 수 없는 키 값으로 변경
        menuToDetach.setMenuName("수박죽");
        entityManager.merge(menuToDetach); // 영속 상태의 엔티티와 병합해야 하지만 존재하지 않을 경우 삽입 된다.(DB에 없는 키값을 merge하면 삽입이 된다.)
        //then
        Menu mergedMenu = entityManager.find(Menu.class, 2);
        assertEquals("수박죽", mergedMenu.getMenuName());
    }
}
