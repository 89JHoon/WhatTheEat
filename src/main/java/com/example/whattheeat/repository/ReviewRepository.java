package com.example.whattheeat.repository;

import com.example.whattheeat.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByOrderId(Long orderId);

    List<Review> findByOrderShopId(Long shopId);

    List<Review> findAllByShopId(Integer shopId);

    // 특정 가게의 모든 리뷰 조회 (order 테이블을 통해 shop_id 연결)
    @Query("SELECT r FROM Review r JOIN r.order o WHERE o.shopId = :shopId")
    List<Review> findByShopId(@Param("shopId") Integer shopId);
}
