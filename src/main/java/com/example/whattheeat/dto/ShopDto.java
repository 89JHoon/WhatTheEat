package com.example.whattheeat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class ShopDto {
    private final String name;
    private final Integer minimumPrice;
    private final LocalTime openTime;
    private final LocalTime closeTime;

}
