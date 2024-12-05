package com.example.whattheeat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Review extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //한 주문에 하나의 리뷰만 쓸 수 있음
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    //별점 1~5점
    @Column(nullable = false)
    private int rating;

    //리뷰 내용
    @Column(nullable = false, length = 500)
    private String content;

    public Review(Order order, int rating, String content) {
        this.order = order;
        this.rating = rating;
        this.content = content;
    }
}
