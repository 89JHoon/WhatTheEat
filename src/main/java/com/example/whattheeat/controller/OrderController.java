package com.example.whattheeat.controller;

import com.example.whattheeat.dto.OrderRequestDto;
import com.example.whattheeat.dto.OrderResponseDto;
import com.example.whattheeat.entity.Order;
import com.example.whattheeat.repository.OrderRepository;
import com.example.whattheeat.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

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

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getOrders(HttpSession session, @RequestParam(required = false) Long shopId){
        Long authenticatedUserId = (Long) session.getAttribute("authenticatedUserId");
        String role = (String) session.getAttribute("authenticatedUserRole");

        if(authenticatedUserId == null || role == null){
            throw new IllegalArgumentException("로그인을 해주세요.");
        }

        if("CUSTOMER".equals(role)){
            return ResponseEntity.ok(orderService.getOrderByCustomer(authenticatedUserId));
        }else if("OWNER".equals(role)){
            if(shopId == null){
                throw new IllegalArgumentException("가게 ID를 입력해주세요");
            }

            Shop shop = shopRepository.findById(shopId)
                    .orElseThrow(() -> new IllegalArgumentException("가게를 찾을 수 없습니다."));

            if(!shop.getOwner().getId().equals(authenticatedOwnerId)){
                throw new IllegalArgumentException("해당 가게의 주문 내역을 조회할 권한이 없습니다.");
            }

            List<Order> orders = orderRepository.findByShopId(shopId);

            return orders.stream()
                    .map(order -> new OrderResponseDto(order))
                    .collect(Collectors.toList());
        }
    }
}
