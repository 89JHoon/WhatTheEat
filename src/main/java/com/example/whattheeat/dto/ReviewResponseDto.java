package com.example.whattheeat.dto;

import lombok.Getter;

@Getter
public class ReviewResponseDto {

    private Long id;
    private Long orderId;
    private int rating;
    private String content;

    public ReviewResponseDto(Long id, Long orderId, int rating, String content){
        this.id = id;
        this.orderId = orderId;
        this.rating = rating;
        this.content = content;
    }
}
