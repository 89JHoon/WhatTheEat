package com.example.whattheeat.controller;

import com.example.whattheeat.dto.ReviewRequestDto;
import com.example.whattheeat.dto.ReviewResponseDto;
import com.example.whattheeat.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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



    /*
    - 리뷰 조회
    - 리뷰는 단건 조회할 수 없습니다.
    - 리뷰는 가게 정보를 기준으로 다건 조회 가능합니다.
        - 생성일자 기준 최신순으로 정렬합니다.
        - 본인이 작성한 리뷰는 보이지 않습니다.
        - 리뷰를 별점 범위에 따라 조회할 수 있습니다.
            - ex) 3~5점
     */
    @GetMapping("/Shop/{shopId}")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByShop(
            @PathVariable Long shopId,
            @RequestParam(defaultValue = "1") int minRating,
            @RequestParam(defaultValue = "5") int maxRating,
            @SessionAttribute("authenticatedUserId") Long customerId) {
        return ResponseEntity.ok(reviewService.getReviewsByShop(shopId, minRating, maxRating, customerId));
    }
}
