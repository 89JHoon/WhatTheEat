package com.example.whattheeat.service;

import com.example.whattheeat.dto.MenuResponseDto;
import com.example.whattheeat.dto.MenuUpdateResponseDto;
import com.example.whattheeat.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.math.BigDecimal;

import static java.awt.SystemColor.menu;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;


    // 메뉴 생성
    public MenuResponseDto createMenu(int shopId, String name, BigDecimal price){
        Menu menu = new Menu(name,price); //메뉴 객체 생성
        Menu savedMenu = menuRepository.save(menu); //메뉴 생성
        return new MenuResponseDto( savedMenu.getName(),savedMenu.getPrice()); //dto반환

    }

    public MenuUpdateResponseDto updateMenu(int shopId, String name, BigDecimal price){
    menu.updateMenu(name,price);//메뉴 수정
    menuRepository.save(menu);

    return new MenuUpdateResponseDto(menu.getName(),menu.getPrice());
    }
}