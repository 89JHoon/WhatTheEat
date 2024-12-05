package com.example.whattheeat.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MenuResponseDto {
    private int shopId;
    private int id;
    private String name;
    private BigDecimal price;

    public MenuResponseDto(int shopId, int id, String name, BigDecimal price) {
    this.shopId = shopId;
    this.id = id;
    this.name = name;
    this.price = price;}
}

