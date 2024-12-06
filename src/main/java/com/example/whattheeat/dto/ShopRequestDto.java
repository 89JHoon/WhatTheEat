package com.example.whattheeat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ShopRequestDto {
    private final Long id;
    private final String name;
    private final Integer minimumPrice;
    private final LocalTime openTime;
    private final LocalTime closeTime;
    private final String state;

    private final List<MenuResponseDto> menus;

    public ShopRequestDto(Long id, String name, LocalTime openTime, LocalTime closeTime,
                           Integer minimumPrice, String state, List<MenuResponseDto> menus) {
        this.id = id;
        this.name = name;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minimumPrice = minimumPrice;
        this.state = state;
        this.menus = menus;
    }
}
