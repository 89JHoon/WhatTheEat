package com.example.whattheeat.repository;

import com.example.whattheeat.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    //리뷰 조회 관련
    @Query("SELECT r FROM Review r JOIN r.order o " +
            "WHERE o.shopId = :shopId " +
            "AND r.rating BETWEEN :minRating AND :maxRating " +
            "AND o.customer.id != :customerId " +
            "ORDER BY r.createdAt DESC")
    List<Review> findByShopIdAndRatingBetween(
            @Param("shopId") Integer shopId,
            @Param("minRating") int minRating,
            @Param("maxRating") int maxRating,
            @Param("customerId") Long customerId
    );

}
