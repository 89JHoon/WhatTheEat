package com.example.whattheeat.service;

import com.example.whattheeat.dto.ReviewRequestDto;
import com.example.whattheeat.dto.ReviewResponseDto;
import com.example.whattheeat.entity.Order;
import com.example.whattheeat.entity.Review;
import com.example.whattheeat.repository.OrderRepository;
import com.example.whattheeat.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public ReviewResponseDto createReview(ReviewRequestDto requestDto, Long authenticatedCustomerId){
        Order order = orderRepository.findById(requestDto.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));

        if(!order.getCustomer().getId().equals(authenticatedCustomerId)){
            throw new IllegalArgumentException("리뷰를 작성할 권한이 없습니다.");
        }

    }
}
