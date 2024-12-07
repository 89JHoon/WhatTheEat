package com.example.whattheeat.service;

import com.example.whattheeat.dto.MenuResponseDto;
import com.example.whattheeat.dto.MenuUpdateResponseDto;
import com.example.whattheeat.entity.Shop;
import com.example.whattheeat.entity.Menu;
import com.example.whattheeat.exception.CustomException;
import com.example.whattheeat.repository.MenuRepository;
import com.example.whattheeat.repository.ShopRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final ShopRepository shopRepository;


    @Transactional
    // 메뉴 생성
    public MenuResponseDto createMenu(Long userId, Long shopId, String name, BigDecimal price) {
        //가게 확인
        Shop shop = checkShopAndOwner(shopId, userId);
        //메뉴 객체 생성
        Menu menu = new Menu(shop, name, price);
        //메뉴 생성
        Menu savedMenu = menuRepository.save(menu);
        //dto반환
        return new MenuResponseDto(shop.getId(), savedMenu.getId(), savedMenu.getName(), savedMenu.getPrice());

    }

    @Transactional
    public MenuUpdateResponseDto updateMenu(Long userId, Long shopId, Long menuId, String name, BigDecimal price) {
        //가게 확인
        checkShopAndOwner(shopId, userId);
        //메뉴 확인
        Menu menu = checkMenu(shopId, menuId);
        //메뉴 수정
        menu.updateMenu(name, price);
        menuRepository.save(menu);
        //dto반환
        return new MenuUpdateResponseDto(menu.getName(), menu.getPrice());
    }

    @Transactional
    public void deleteMenu(Long userId, Long shopId, Long menuId) {
        checkShopAndOwner(shopId, userId);
        Menu menu = checkMenu(shopId, menuId);
        menuRepository.delete(menu);
    }

    private Menu checkMenu(Long shopId, Long menuId) {
        //메뉴 존재 여부 확인
        Menu menu = menuRepository.findByIdAndIsDeletedFalse(menuId)
                .orElseThrow(() -> new CustomException("메뉴를 찾을 수 없습니다.(이미 삭제 된 메뉴입니다.)"));
        //메뉴가 해당 가게의 메뉴인지 확인
        if (!Objects.equals(menu.getShop().getId(), shopId)) {
            throw new CustomException("이 가게의 메뉴가 아닙니다.");
        }
        return menu;
    }

    private Shop checkShopAndOwner(Long shopId, Long userId) {
        //가게 확인
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new CustomException("가게가 존재하지 않습니다."));

        //로그인 사용자가 가게 주인인지 확인
        if (!Objects.equals(shop.getOwner().getId(), userId)) {
            throw new CustomException("이 가게의 사장이 아닙니다.");
        }
        return shop;
    }
}