package com.ohgiraffers.springdatajpa.menu.controller;

import com.ohgiraffers.springdatajpa.common.Pagenation;
import com.ohgiraffers.springdatajpa.common.PagingButtonInfo;
import com.ohgiraffers.springdatajpa.menu.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Controller
@RequestMapping("/menu")
public class MenuController {

    private MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/{menuCode}")
    public String findMenuByCode(@PathVariable int menuCode, Model model) {

        MenuDTO menu = menuService.findMenuByCode(menuCode);
        model.addAttribute("menu", menu);

        return "menu/detail";
    }

    /* 페이징 이전 버전 */
//    @GetMapping("/list")
//    public String findMenuList(Model model) {
//        List<MenuDTO> menuList = menuService.findMenuList();
//        model.addAttribute("menuList", menuList);
//        return "menu/list";
//    }

    /* 페이징 이후 버전 */
    @GetMapping("/list")
    public String findMenuList (@PageableDefault Pageable pageable, Model model) {

        /* page -> number, size, sort 파라미터가 Pageable 객체에 담긴다. */
        log.info("pageable : {}", pageable);

        Page<MenuDTO> menuList = menuService.findMenuList(pageable);

        /* Page 객체가 담고 있는 정보 */
        log.info("조회한 내용 목록 ㅣ {}", menuList.getContent());
        log.info("총 페이지 수  : {}", menuList.getTotalPages());
        log.info("총 메뉴 수 : {}", menuList.getTotalElements());
        log.info("해당 페이지에 표시 될 요소 수 : {}", menuList.getSize());
        log.info("해당 페이지에 실제 요소 수 : {}", menuList.getNumberOfElements());
        log.info("첫 페이지 여부 : {}", menuList.isFirst());
        log.info("마지막 페이지 여부 : {}", menuList.isLast());
        log.info("정렬 방식 : {}", menuList.getSort());
        log.info("여러 페이지 중 현재 인덱스 : {}", menuList.getNumber());

        PagingButtonInfo paging = Pagenation.getPagingButtonInfo(menuList);

        model.addAttribute("menuList", menuList);
        model.addAttribute("paging", paging);

        return "menu/list";
    }

    @GetMapping("/querymethod")
    public void queryMethodPage(){}

    @GetMapping("/search")
    public String findByMenuPrice(@RequestParam Integer menuPrice, Model model){

        List<MenuDTO> menuList = menuService.findByMenuPrice(menuPrice);


        model.addAttribute("menuList", menuList);

        return "menu/searchResult";
    }

    @GetMapping("/searchName")
    public String findByMenuName(@RequestParam String menuName, Model model) {

        List<MenuDTO> menuList = menuService.findByMenuName(menuName);
        model.addAttribute("menuList", menuList);

        return "menu/searchName";
    }

    @GetMapping("/searchCategory")
    public String findByCategoryCode(@RequestParam Integer categoryCode, Model model) {

        List<MenuDTO> menuList = menuService.findByCategoryCode(categoryCode);
        model.addAttribute("menuList", menuList);

        return "menu/searchCategory";
    }

    @GetMapping("/categoryList")
    @ResponseBody
    public List<CategoryDTO> findCategoryList1(){
        return menuService.findAllCategory();
    }

    @GetMapping("/searchNameAndPrice")
    public String findByNameAndPrice(@RequestParam Integer menuPrice,
                                     @RequestParam String menuName,
                                     Model model) {

        List<MenuDTO> menuList = menuService.findByNameAndPrice(menuPrice, menuName);
        model.addAttribute("menuList", menuList);

        return "menu/searchNameAndPrice";
    }


    @GetMapping("/regist")
    public void registPage() {}

    @GetMapping("/category")
    @ResponseBody
    public List<CategoryDTO> findCategoryList(){
        return menuService.findAllCategory();
    }

    @PostMapping("/regist")
    public String registMenu(MenuDTO menu) {

        menuService.registNewMenu(menu);

        return "redirect:/menu/list";
    }

    @GetMapping("/modify")
    public void modifyPage(){}

    @PostMapping("/modify")
    public String modifyMenu(MenuDTO menu) {

        menuService.modifyMenu(menu);

        return "redirect:/menu/" + menu.getMenuCode();
    }

    @GetMapping("/delete")
    public void deletePage() {}

    @PostMapping("/delete")
    public String deleteMenu(@RequestParam Integer menuCode) {

        menuService.deleteMenu(menuCode);

        return "redirect:/menu/list";
    }
}
