package com.example.whattheeat.controller;

import com.example.whattheeat.dto.OrderRequestDto;
import com.example.whattheeat.dto.OrderResponseDto;
import com.example.whattheeat.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    //주문 - 고객
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto requestDto,
                                                        HttpSession session){
        Long authenticatedCustomerId = (Long) session.getAttribute("authenticatedUserId");
        String role = (String) session.getAttribute("authenticatedUserRole");

        if(authenticatedCustomerId == null || !"CUSTOMER".equals(role)){
            throw new IllegalArgumentException("로그인한 고객만 주문을 할 수 있습니다.");
        }

        OrderResponseDto responseDto = orderService.createOrder(requestDto, authenticatedCustomerId);

        return ResponseEntity.ok(responseDto);
    }

    //주문 상태 변경 - 사장님
    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody OrderRequestDto requestDto,
            HttpSession session
    ){
        Long authenticatedOwnerId = (Long) session.getAttribute("authenticatedUserId");
        String role = (String) session.getAttribute("authenticatedUserRole");

        if(authenticatedOwnerId == null || !"OWNER".equals(role)){
            throw new IllegalArgumentException("사장님만 주문 상태를 변경할 수 있습니다.");
        }

        OrderResponseDto responseDto = orderService.updateOrderStatus(orderId,authenticatedOwnerId, requestDto.getOrderStatus());
        return ResponseEntity.ok(responseDto);
    }

}
