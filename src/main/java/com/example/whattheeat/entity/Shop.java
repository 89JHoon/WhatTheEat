package com.example.whattheeat.entity;


import com.example.whattheeat.enums.ShopState;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;


@Entity
@Table(name = "shop")
@NoArgsConstructor
@Getter
@Setter
@Builder

public class Shop extends BaseEntity {

    //가게 ID
    //기본 키 설정 ID 자동 생성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //가게 이름
    //가게 이름은 중복이 안됨
    @Column(name = "name", unique = true, length = 50)
    private String name;


      @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name = "user_id")
      private User user;

    //가게 영업 시간
    @Column(name = "opentime", columnDefinition = "TIME")
    private LocalTime openTime;

    @Column(name = "closetime", columnDefinition = "TIME")
    private LocalTime closeTime;


    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // 가게 최소 주문
    @Column(name = "minimum_price")
    private Integer minimumPrice;

    //가게 상태
    @Enumerated(EnumType.STRING)
    private ShopState state;


    @Builder
    public Shop(String name, User userId, LocalTime openTime, LocalTime closeTime, Integer minimumPrice, ShopState state) {
        this.name = name;
        this.user = userId;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minimumPrice = minimumPrice;
        this.state = state;

    }
}
