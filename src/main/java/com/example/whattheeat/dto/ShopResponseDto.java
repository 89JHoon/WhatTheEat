package com.example.whattheeat.dto;

import com.example.whattheeat.entity.Shop;
import lombok.Getter;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ShopResponseDto {
    private final Long id;
    private final String name;
    private final Integer minimumPrice;
    private final LocalTime openTime;
    private final LocalTime closeTime;
    private final String state;

    private final List<MenuResponseDto> menus;  // 메뉴 리스트 추가

    public ShopResponseDto(Long id, String name, Integer minimumPrice, LocalTime openTime, LocalTime closeTime,
                            String state, List<MenuResponseDto> menus) {
        this.id = id;
        this.name = name;
        this.minimumPrice = minimumPrice;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.state = state;
        this.menus = menus;
    }

    public static ShopResponseDto from(Shop shop) {

        List<MenuResponseDto> menuResponses = shop.getMenus() != null
                ? shop.getMenus().stream()
                .map(menu -> new MenuResponseDto(
                        shop.getId(),
                        menu.getId(),
                        menu.getName(),
                        menu.getPrice()
                ))
                .collect(Collectors.toList())
                :Collections.emptyList();

        return new ShopResponseDto(
                shop.getId(),
                shop.getName(),
                shop.getMinimumPrice(),
                shop.getOpenTime(),
                shop.getCloseTime(),
                shop.getState().toString(),
                menuResponses
        );
    }
    public ShopResponseDto(Shop shop) {
        this.id = shop.getId();
        this.name = shop.getName();
        this.minimumPrice = shop.getMinimumPrice();
        this.openTime = shop.getOpenTime();
        this.closeTime = shop.getCloseTime();
        this.state = "";
        this.menus = Collections.emptyList(); // 빈 리스트로 초기화
    }

}