package com.ohgiraffers.section02.crud;

import javax.persistence.*;

@Entity(name = "section02_menu")   // 다른 패키지에 동일 이름의 클래스가 존재하면 오류 발생 -> 구분하기 위해 이름 지정(필수는 아니며 지정하지 않을 경우 클래스명을 따라감)
@Table(name = "TBL_MENU")
@SequenceGenerator(
        name = "seq_menu_code_generator",
        sequenceName = "SEQ_MENU_CODE",  //사용하는 Object의 이름
        initialValue = 100,  //초기값(시퀀스가 없을 시 이 설정을 따르지만 기존에 있으면 DB 설정에 따름)
        allocationSize = 1   //증가값(시퀀스가 없을 시 이 설정을 따르지만 기존에 있으면 DB 설정에 따름)
)
public class Menu {

    @Id  //Entity가 가져야 할 PrimaryKey를 Id로 적어줌
    @Column(name = "MENU_CODE")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_menu_code_generator" // 상단 @SequenceGenerator의 name과 동일
    )
    private int menuCode;

    @Column(name = "MENU_NAME")
    private String menuName;

    @Column(name = "MENU_PRICE")
    private int menuPrice;

    @Column(name = "CATEGORY_CODE")
    private int categoryCode;

    @Column(name = "ORDERABLE_STATUS")
    private String orderableStatus;

    public Menu() {
    }

    public Menu(int menuCode, String menuName, int menuPrice, int categoryCode, String orderableStatus) {
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.categoryCode = categoryCode;
        this.orderableStatus = orderableStatus;
    }

    public int getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(int menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(int menuPrice) {
        this.menuPrice = menuPrice;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getOrderableStatus() {
        return orderableStatus;
    }

    public void setOrderableStatus(String orderableStatus) {
        this.orderableStatus = orderableStatus;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuCode=" + menuCode +
                ", menuName='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                ", categoryCode=" + categoryCode +
                ", orderableStatus='" + orderableStatus + '\'' +
                '}';
    }
}
