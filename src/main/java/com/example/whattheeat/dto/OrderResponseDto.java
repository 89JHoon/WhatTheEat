package com.example.whattheeat.dto;

import com.example.whattheeat.enums.OrderStatus;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderResponseDto {

    private Long id;
    private Long customerId;
    private Long menuId;
    private Long shopId;
    private int quantity;
    private BigDecimal totalPrice;
    private String address;
    private OrderStatus orderStatus;

    public OrderResponseDto(Long id, Long customerId, Long menuId, Long shopId, int quantity, BigDecimal totalPrice, String address, OrderStatus orderStatus) {
        this.id = id;
        this.customerId = customerId;
        this.menuId = menuId;
        this.shopId = shopId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.address = address;
        this.orderStatus = orderStatus;
    }
}
