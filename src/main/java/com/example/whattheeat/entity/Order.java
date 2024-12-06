package com.example.whattheeat.entity;

import com.example.whattheeat.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


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
    private int quantity;

    //총 주문 가격
    //calculateTotalPrice() 메서드에 의해 계산
    @Column(nullable = false)
    private BigDecimal totalPrice;

    //배달 주소
    //고객이 입력한 주소 저장
    @Column(nullable = false)
    private String address;

    //주문 엔티티 생성자를 통해 필수 정보를 초기화
    public Order(User customer, Menu menu, Shop shop, int quantity, String address){
        this.customer = customer;
        this.menu = menu;
        this.shop = shop;
        this.quantity = quantity;
        this.address = address;
        this.totalPrice = menu.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    public void updateOrderStatus(OrderStatus newStatus){
        //배달 완료 상태인지 확인
        if(this.orderStatus == OrderStatus.DELIVERED){
            //배달이 완료된 상태라면 예외 발생
            //상태를 변경할 수 없음
            throw new IllegalArgumentException("배달이 완료되어 주문 상태를 변경할 수 없습니다.");
        }
        if(!this.orderStatus.canTransitionTo(newStatus)){
            throw new IllegalArgumentException("잘못된 주문 상태 전환입니다: " +
                    this.orderStatus + "에서" + newStatus + "로 변경할 수 없습니다.");
        }
        //배달 완료 상태이면 상태변경 못함
        //상태변경은 순서대로만 가능
        this.orderStatus = newStatus;
    }
}