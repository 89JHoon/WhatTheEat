package com.example.whattheeat.controller;

import com.example.whattheeat.dto.OrderRequestDto;
import com.example.whattheeat.dto.OrderResponseDto;
import com.example.whattheeat.dto.OrderStatusRequestDto;
import com.example.whattheeat.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    //주문 - 고객
    //현재 로그인했고, 고객인지 확인
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @SessionAttribute("authenticatedUserId") Long customerId,
            @SessionAttribute("authenticatedUserRole") String role,
            @RequestBody @Valid OrderRequestDto requestDto) {
        //고객인지 검증
        if (!"CUSTOMER".equals(role)) {
            throw new IllegalArgumentException("고객만 주문할 수 있습니다.");
        }
        //주문 생성 서비스 호출
        return ResponseEntity.ok(orderService.createOrder(requestDto, customerId));
    }

    //주문 상태 변경 - 사장님
    //현재 로그인했고, 사장님인지 확인
    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(
            @SessionAttribute("authenticatedUserId") Long ownerId,
            @SessionAttribute("authenticatedUserRole") String role,
            @PathVariable Long orderId,
            @RequestBody @Valid OrderStatusRequestDto statusRequestDto){

        //사장님인지 검증
        if(!"OWNER".equals(role)){
            throw new IllegalArgumentException("사장님만 주문 상태를 변경할 수 있습니다.");
        }
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId,ownerId,statusRequestDto.getNewStatus()));
    }

    //주문조회
    //고객 : 자신의 주문 내역 조회
    //사장님 : 특정 가게의 주문 내역 조회
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getOrders(
            @SessionAttribute("authenticatedUserId") Long userId,
            @SessionAttribute("authenticatedUserRole") String role,
            @RequestParam(required = false) Long shopId){
        //고객일 경우 자신의 주문 내역 조회
        if("CUSTOMER".equals(role)){
            return ResponseEntity.ok(orderService.getOrderByCustomer(userId));
        }else if("OWNER".equals(role)){
            //사장님일 경우 자신의 가게 중 특정 가게의 주문 내역 조회
            if(shopId == null){
                throw new IllegalArgumentException("가게 ID를 입력해주세요.");
            }
            return ResponseEntity.ok(orderService.getOrderByShop(shopId, userId));
        }else {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
    }
}
