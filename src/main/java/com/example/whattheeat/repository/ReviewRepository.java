package com.example.whattheeat.repository;

import com.example.whattheeat.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByOrderId(Long orderId);

    List<Review> findByOrderShopId(Long shopId);
}
