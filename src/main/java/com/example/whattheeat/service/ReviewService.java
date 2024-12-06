package com.example.whattheeat.service;

import com.example.whattheeat.dto.ReviewRequestDto;
import com.example.whattheeat.dto.ReviewResponseDto;
import com.example.whattheeat.entity.Order;
import com.example.whattheeat.entity.Review;
import com.example.whattheeat.enums.OrderStatus;
import com.example.whattheeat.exception.CustomException;
import com.example.whattheeat.repository.OrderRepository;
import com.example.whattheeat.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    //리뷰 생성
    @Transactional
    public ReviewResponseDto createReview(Long customerId, ReviewRequestDto requestDto){
        //주문 확인
        Order order = orderRepository.findById(requestDto.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));

        //주문 고객 확인
        if(!order.getCustomer().getId().equals(customerId)){
            throw new IllegalArgumentException("해당 주문에 대한 리뷰를 작성할 권한이 없습니다.");
        }

        //리뷰 작성 가능 여부 - 배달 완료 상태일 경우 리뷰 작성 가능
        if(!order.getOrderStatus().equals(OrderStatus.DELIVERED)){
            throw new IllegalArgumentException("배달 완료된 주문만 리뷰를 작성할 수 있습니다.");
        }

        //리뷰는 한번만 작성 가능
        if(reviewRepository.existsByOrderId(order.getId())){
            throw new IllegalArgumentException("이미 작성된 리뷰가 있습니다.");
        }

        //리뷰 저장
        Review review = new Review(order, requestDto.getRating(), requestDto.getContent());
        reviewRepository.save(review);

        return new ReviewResponseDto(review.getId(), review.getOrder().getId(), review.getRating(), review.getContent());
    }

    //리뷰 조회
  //  @Transactional(readOnly = true)
    @Transactional
    public List<ReviewResponseDto> getReviewsByShop(Long shopId, int minRating, int maxRating, Long customerId) {
        List<Review> reviews = reviewRepository.findByShopIdAndRatingBetween(shopId, minRating, maxRating, customerId);

        if (reviews.isEmpty()) {
            throw new CustomException("등록된 리뷰가 없습니다.");
        }

        return reviews.stream()
                .map(review -> new ReviewResponseDto(
                        review.getId(),
                        review.getOrder().getId(),
                        review.getRating(),
                        review.getContent()
                ))
                .collect(Collectors.toList());
    }
}
