package com.example.whattheeat.enums;

//주문 상태를 나타냄
//주문 완료 -> 주문 수락 -> 조리 중 -> 조리 완료 -> 배달 중 -> 배달 완료
public enum OrderStatus {
    ORDERED, //주문 접수
    ACCEPTED, //주문 수락
    PREPARING, //조리 중
    COOKED, //조리 완료
    DELIVERING, //배달 중
    DELIVERED //배달 완료
}
