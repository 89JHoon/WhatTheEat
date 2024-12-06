package com.example.whattheeat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class ShopRequestDto {
    private  final String name;
    private  final LocalTime openTime;
    private  final LocalTime closeTime;
    private  final Integer minimumPrice;
    private final String state;
}
