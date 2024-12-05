package com.example.whattheeat.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReviewRequestDto {

    @NotNull
    private Long orderId;

    @Min(1)
    @Max(5)
    private int rating;

    @NotBlank
    private String content;
}
