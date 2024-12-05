package com.example.whattheeat.dto;

import com.example.whattheeat.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

//주문 요청 데이터를 담는 dto
@Getter
public class OrderRequestDto {
    @NotNull
    private Long customerId;

    @NotNull
    private Long menuId;

    @NotNull
    private Long shopId;

    @NotNull
    private int quantity;

    @NotNull
    private String address;

    private OrderStatus orderStatus;
}
