package com.example.whattheeat.controller;

import com.example.whattheeat.entity.Review;
import com.example.whattheeat.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
private ReviewService reviewService;

    //가게별 리뷰 조회
    @GetMapping("/shop/{id}")
    public ResponseEntity<List<Review>> getAllReviewById(@PathVariable Integer shopId){
        return ResponseEntity.ok(reviewService.getAllReviewById(shopId));
    }

}
