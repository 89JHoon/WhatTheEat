package com.example.whattheeat.dto;

import lombok.Getter;

@Getter

public class MenuRequestDto {

    private int shopId;
    private String name;
    private int price;

    public MenuRequestDto(int shopId, String name, int price) {
        this.shopId = shopId;
        this.name = name;
        this.price = price;

    }
}
