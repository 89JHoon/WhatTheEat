package com.example.whattheeat.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter

public class MenuRequestDto {

    private String name;
    private BigDecimal price;
}