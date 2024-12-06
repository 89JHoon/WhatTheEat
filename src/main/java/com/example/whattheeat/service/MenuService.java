package com.example.whattheeat.service;

import com.example.whattheeat.dto.MenuRequestDto;
import com.example.whattheeat.dto.MenuResponseDto;
import com.example.whattheeat.dto.MenuUpdateResponseDto;
import com.example.whattheeat.repository.MenuRepository;
import com.example.whattheeat.repository.ShopRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Objects;

import static java.awt.SystemColor.menu;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final ShopRepository shopRepository;


    @Transactional
    // 메뉴 생성
    public MenuResponseDto createMenu(int userId, int shopId, String name, BigDecimal price) {
        //가게 확인
        Shop shop = checkShopAndMaster(shopId, userId);
        //메뉴 객체 생성
        Menu menu = new Menu(shop, name, price);
        //메뉴 생성
        Menu savedMenu = menuRepository.save(menu);
        //dto반환
        return new MenuResponseDto(shop.getId(), savedMenu.getId(), savedMenu.getName(), savedMenu.getPrice());

    }

    public MenuUpdateResponseDto updateMenu(int userId, int shopId, int menuId, String name, BigDecimal price) {
        //가게 확인
        checkShopAndMaster(shopId, userId);
        //메뉴 확인
        Menu menu = checkMenu(shopId, menuId);
        //메뉴 수정
        menu.updateMenu(name, price);
        menuRepository.save(menu);
        //dto반환
        return new MenuUpdateResponseDto(menu.getName(), menu.getPrice());
    }

    public void deleteMenu(int userId, int shopId, int menuId) {
        checkShopAndMaster(shopId, userId);
        Menu menu = checkMenu(shopId, menuId);
        menu.updateDeleted(true);
        menuRepository.save(menu);
    }

    private Menu checkMenu(int shopId, int menuId) {
        //메뉴 존재 여부 확인
        Menu menu = menuRepository.findMenuByIdOrElseThrow(menuId);
        //메뉴가 해당 가게의 메뉴인지 확인
        if (!Objects.equals(menu.getShop().getId(), shopId)) {
            throw new CustomException(ErrorCode.MENU_NOT_FOUND);
        }
        //메뉴 삭제 여부 확인
        if (menu.getIsDeleted().equals(true)) {
            throw new CustomException(ErrorCode.ALREADY_DELETE_MENU);
        }
        return menu;
    }

    private Shop checkShopAndMaster(int shopId, int userId) {
        //가게 확인
        Shop shop = shopService.findShopById(shopId);

        //로그인 사용자가 가게 주인인지 확인
        if (!Objects.equals(userId, shop.getUser().getId())) {
            throw new CustomException(ErrorCode.NOT_OWNER_CRUD);
        }
        return shop;
    }
}