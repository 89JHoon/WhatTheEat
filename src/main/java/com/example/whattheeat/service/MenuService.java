package com.example.whattheeat.service;

import com.example.whattheeat.dto.MenuResponseDto;
import com.example.whattheeat.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;


    // 메뉴 생성
    public MenuResponseDto createMenu(int shopId, String name, int price){

    }