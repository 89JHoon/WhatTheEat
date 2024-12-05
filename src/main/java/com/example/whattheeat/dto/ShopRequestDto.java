package com.example.whattheeat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class ShopRequestDto {
    private String name;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Integer minimumPrice;
}
