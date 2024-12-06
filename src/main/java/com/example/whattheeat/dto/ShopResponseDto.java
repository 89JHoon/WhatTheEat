package com.example.whattheeat.dto;

import com.example.whattheeat.entity.Shop;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ShopResponseDto {
    private final Long id;
    private final String name;
    private final Integer minimumPrice;
    private final LocalTime openTime;
    private final LocalTime closeTime;
    private final String state;

    private final List<MenuResponseDto> menus;  // 메뉴 리스트 추가


    public ShopResponseDto(Long id, String name, LocalTime openTime, LocalTime closeTime,
                           Integer minimumPrice, String state, List<MenuResponseDto> menus) {
        this.id = id;
        this.name = name;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minimumPrice = minimumPrice;
        this.state = state;
        this.menus = menus;
    }

    public static ShopResponseDto from(Shop shop) {
        return new ShopResponseDto(
                shop.getId(),
                shop.getName(),
                shop.getMinimumPrice(),
                shop.getOpenTime(),
                shop.getCloseTime(),
                shop.getState().toString(),
                shop.getMenus().stream()
                        .map(menu -> new MenuResponseDto(
                                menu.getId(),
                                menu.getName(),
                                menu.getPrice()
                        ))
                        .collect(Collectors.toList())
        );
    }


}

