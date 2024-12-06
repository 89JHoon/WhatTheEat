package com.example.whattheeat.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MenuResponseDto {
    private final Long shopId;
    private final Long id;
    private final String name;
    private final BigDecimal price;

    public MenuResponseDto(Long shopId, Long id, String name, BigDecimal price) {
        this.shopId = shopId;
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

