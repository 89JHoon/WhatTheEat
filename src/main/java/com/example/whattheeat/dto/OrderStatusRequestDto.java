package com.example.whattheeat.dto;

import com.example.whattheeat.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderStatusRequestDto {

    @NotNull
    private OrderStatus newStatus;
}
