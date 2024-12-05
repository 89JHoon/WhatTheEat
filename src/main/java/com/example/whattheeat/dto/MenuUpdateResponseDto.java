package com.example.whattheeat.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MenuUpdateResponseDto {

    private String name;
    private BigDecimal price;

    public MenuUpdateResponseDto( String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }
}
