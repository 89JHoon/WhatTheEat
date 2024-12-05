package com.example.whattheeat.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReviewRequestDto {

    @NotNull(message = "별점은 필수 입력값입니다.")
    @Min(value = 1, message = "별점은 최소 1점이어야 합니다.")
    @Max(value = 5, message = "별점은 최대 5점이어야 합니다.")
    private int rating;

    @NotBlank(message = "리뷰 내용은 필수 입력값입니다.")
    private String content;

    @NotNull(message = "주문 ID는 필수 입력값입니다.")
    private Long orderId;
}
