package com.example.whattheeat.entity;

import com.example.whattheeat.enums.ShopState;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "shop")
@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class Shop extends BaseEntity {

    //가게 ID
    //기본 키 설정 ID 자동 생성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //가게 이름
    //가게 이름은 중복이 안됨
    @Column(name = "name", unique = true, nullable = false, length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    //가게 영업 시간
    @Column(name = "opentime", columnDefinition = "TIME", nullable = false)
    private LocalTime openTime;

    @Column(name = "closetime", columnDefinition = "TIME", nullable = false)
    private LocalTime closeTime;

    // 가게 최소 주문
    @Column(name = "minimum_price", nullable = false)
    private Integer minimumPrice;

    //가게 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShopState state;

    //가게 조회시 메뉴도 함께
    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menus;

    //폐업
    public void closeShop(){
        LocalDateTime now = LocalDateTime.now();
        this.deletedAt = now;
        this.state = ShopState.CLOSED;
        this.setDeletedAt(LocalDateTime.now());

        if (this.menus != null){
            this.menus.forEach(menu -> menu.deleteMenu(now));
        }
    }

    private void setDeletedAt(LocalDateTime deletedAt){
        this.deletedAt = deletedAt;
    }

    //가게 정보 수정
    public void updateShopDetails(LocalTime openTime, LocalTime closeTime, Integer minimumPrice, ShopState state){
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minimumPrice = minimumPrice;
        this.state = state;
    }

    }


