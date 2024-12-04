package com.example.whattheeat.entity;

import com.example.whattheeat.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;

@Entity
@Getter
@NoArgsConstructor //기본 생성자를 생성
public class Order extends BaseEntity{

    //주문 ID
    //기본 키 설정, ID 자동 생성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //주문 고객(customer)
    //고객 테이블과 다대일 관계
    //고객 데이터를 지연로딩
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User customer;

    //주문 메뉴
    //메뉴 테이블과 다대일 관계
    //메뉴 데이터 지연로딩
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    //주문 가게
    //가게 테이블과 다대일 관계
    //가게 데이터를 지연로딩
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    //주문 상태
    //enum 값을 문자열로 지정
    //기본값은 ORDERED
    //ORDERED, ACCEPTED, PREPARING, COOKED, DELIVERING, DELIVERED
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus = OrderStatus.ORDERED;

    //주문 수량
    //기본 값은 1이고, 필수값
    @Column(nullable = false)
    private int quantity = 1;

    //총 주문 가격
    //calculateTotalPrice() 메서드에 의해 계산
    @Column(nullable = false)
    private int totalPrice;

    //배달 주소
    //고객이 입력한 주소 저장
    @Column(nullable = false)
    private String address;

    //주문 엔티티 생성자를 통해 필수 정보를 초기화
    //Builder 패턴을 사용하여 객체 생성의 가독성을 높임
    //orderStatus에 삼항연산자 적용
    //condition ? trueValue : falseValue;
    @Builder
    public Order(User customer, Menu menu, Shop shop, int quantity, String address, OrderStatus orderStatus){
        this.customer = customer;
        this.menu = menu;
        this.shop = shop;
        this.quantity = quantity;
        this.address = address;
        this.orderStatus = orderStatus != null ? orderStatus : OrderStatus.ORDERED;
        this.calculateTotalPrice();
    }

    //총 가격 계산
    //메뉴 가격과 주문 수량을 곱하여 총 가격을 계산
    public void calculateTotalPrice(){
        this.totalPrice = this.menu.getPrice() * this.quantity;
    }
}