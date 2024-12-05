package com.example.whattheeat.service;

import com.example.whattheeat.dto.MenuRequestDto;
import com.example.whattheeat.dto.MenuResponseDto;
import com.example.whattheeat.dto.MenuUpdateResponseDto;
import com.example.whattheeat.repository.MenuRepository;
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
    public MenuResponseDto createMenu(MenuRequestDto menuRequestDto) {
        //가게 확인
        Shop shop = shopRepository.findStoreByIdAndUserIdOrElseThrow(shopId, userId);
        //로그인된 사용자가 가게주인인지 확인
        if(!Objects.equals(//유저아이디, 가게아이디 등등)){
                throw new CustomException(ErrorCode.NOT_OWNER_CRUD);

        Menu menu = new Menu(menuRequestDto); //메뉴 객체 생성
        Menu savedMenu = menuRepository.save(menu); //메뉴 생성
        return new MenuResponseDto(savedMenu); //dto반환

    }

    public MenuUpdateResponseDto updateMenu(MenuRequestDto menuRequestDto){
        //가게 확인
        checkShopAndOwner(shopId,userId);
        //메뉴 확인
        Menu menu = checkMenu(shopId, menuId);
        //메뉴 수정
    menu.updateMenu(menuRequestDto);
    menuRepository.save(menu);
    //dto반환
    return new MenuUpdateResponseDto(menuRequestDto);
    }

    public void deleteMenu(MenuRequestDto menuRequestDto){
        Menu menu = new Menu(menuRequestDto);
        menu.deleteMenu(true);
        menuRepository.save(menu);
    }
}