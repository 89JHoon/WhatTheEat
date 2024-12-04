package com.example.whattheeat.entity;


import com.example.whattheeat.enums.State;
import jakarta.persistence.*;
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
public class ShopEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, length = 50)
    private String name;

    // 유저 테이블 이랑 연결 해야되는거 같음
    //  @ManyToOne(fetch = FetchType.LAZY)
    //  @JoinColumn(name = "user_id")
    //  private UserEntity user;

    // 임시로 User 엔티티 연결 대신 userId만 저장
    @Column(name = "user_id")
    private Integer userId;


    @Column(name = "opentime", columnDefinition = "TIME")
    private LocalTime openTime;

    @Column(name = "closetime", columnDefinition = "TIME")
    private LocalTime closeTime;

    @Column(name = "minimum_price")
    private Integer minimumPrice;

    @Enumerated(EnumType.STRING)
    private State state;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


}
