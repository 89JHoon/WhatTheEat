package com.example.whattheeat.service;

import com.example.whattheeat.dto.OrderRequestDto;
import com.example.whattheeat.dto.OrderResponseDto;
import com.example.whattheeat.entity.Order;
import com.example.whattheeat.enums.OrderStatus;
import com.example.whattheeat.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final ShopRepository shopRepository;

    //주문하기 - 고객 권한
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto, Long authenticatedCustomerId){

        //메뉴와 가게 존재 확인
        Menu menu = menuRepository.findById(requestDto.getMenuId())
                .orElseThrow(() -> new IllegalArgumentException("없는 메뉴입니다."));

        Shop shop = shopRepository.findById(requestDto.getShopId())
                .orElseThrow(() -> new IllegalArgumentException("가게를 찾을 수 없습니다."));

        //주문 금액 계산
        int expectedTotalPrice = menu.getPrice() * requestDto.getQuantity();

        //최소 주문 금액 검증
        if(expectedTotalPrice < shop.getMinimumOrderPrice()){
            throw new IllegalArgumentException("최소 주문 금액을 만족하지 못했습니다.");
        }

        //가게 오픈/마감시간 확인
        LocalTime now = LocalTime.now();
        if(now.isBefore(shop.getOpenTime()) || now.isAfter(shop.getCloseTime())){
            throw new IllegalArgumentException("가게 영업 시간이 아닙니다.");
        }

        Order order = Order.builder()
                .customer(authenticatedCustomerId)
                .menu(menu)
                .shop(shop)
                .quantity(requestDto.getQuantity())
                .address(requestDto.getAddress())
                .orderStatus(OrderStatus.ORDERED)
                .build();

        Order savedOrder = orderRepository.save(order);
        return convertToResponseDto(savedOrder);
    }

    //주문 상태 변경 - 사장님
    @Transactional
    public OrderResponseDto updateOrderStatus(Long orderId, Long authenticatedOwnerId, OrderStatus newStatus){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        if(!order.getShop().getOwner().getId().equals(authenticatedOwnerId)){
            throw new IllegalArgumentException("해당 주문을 변경할 권한이 없습니다.");
        }

        order.setOrderStatus(newStatus);

        return convertToResponseDto(order);
    }

    //주문내역 조회 - 고객
    @Transactional
    public List<OrderResponseDto> getOrderByCustomer(Long authenticatedCustomerId){
        List<Order> orders = orderRepository.findByCustomerId(authenticatedCustomerId);

        return orders.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    //주문내역 조회 - 가게
    @Transactional
    public List<OrderResponseDto> getOrderByShop(Long shopId, Long authenticatedOwnerId){
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new IllegalArgumentException("가게를 찾을 수 없습니다."));

        if(!shop.getOwner().getId().equals(authenticatedOwnerId)){
            throw new IllegalArgumentException("해당 가게의 주문을 조회할 권한이 없습니다.");
        }

        List<Order> orders = orderRepository.findByShopId(shopId);

        return orders.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    private OrderResponseDto convertToResponseDto(Order order){
        return new OrderResponseDto(
                order.getId(),
                order.getCustomer().getId(),
                order.getMenu().getId(),
                order.getShop().getId(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getAddress(),
                order.getOrderStatus()
        );
    }
}
