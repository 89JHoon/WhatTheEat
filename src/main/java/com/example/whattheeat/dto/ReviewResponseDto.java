package com.example.whattheeat.dto;

import com.example.whattheeat.entity.Review;
import lombok.Getter;

@Getter
public class ReviewResponseDto {
    private Long id;
    private Long orderId;
    private int rating;
    private String content;

    public ReviewResponseDto(Review review){
        this.id = review.getId();
        this.orderId = review.getOrder().getId();
        this.rating = review.getRating();
        this.content = review.getContent();
    }
}
