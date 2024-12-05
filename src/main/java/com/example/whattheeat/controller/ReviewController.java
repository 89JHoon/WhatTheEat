package com.example.whattheeat.controller;

import com.example.whattheeat.dto.ReviewRequestDto;
import com.example.whattheeat.dto.ReviewResponseDto;
import com.example.whattheeat.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(
            @SessionAttribute("authenticatedUserId") Long customerId,
            @RequestBody @Valid ReviewRequestDto requestDto){
        return ResponseEntity.ok(reviewService.createReview(customerId, requestDto));
    }
}
