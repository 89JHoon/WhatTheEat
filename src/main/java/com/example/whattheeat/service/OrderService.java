package com.example.whattheeat.service;

import com.example.whattheeat.dto.OrderRequestDto;
import com.example.whattheeat.dto.OrderResponseDto;
import com.example.whattheeat.entity.Menu;
import com.example.whattheeat.entity.Order;
import com.example.whattheeat.entity.ShopEntity;
import com.example.whattheeat.enums.OrderStatus;
import com.example.whattheeat.repository.MenuRepository;
import com.example.whattheeat.repository.OrderRepository;
import com.example.whattheeat.repository.ShopRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public OrderResponseDto createOrder(OrderRequestDto requestDto, Long customerId){

        //메뉴와 가게 존재 확인
        Menu menu = menuRepository.findMenuById(requestDto.getMenuId())
                .orElseThrow(() -> new IllegalArgumentException("없는 메뉴입니다."));

        ShopEntity shop = shopRepository.findById(requestDto.getShopId())
                .orElseThrow(() -> new IllegalArgumentException("가게를 찾을 수 없습니다."));

        //주문 금액 계산
        BigDecimal totalPrice = menu.getPrice().multiply(BigDecimal.valueOf(requestDto.getQuantity()));

        //최소 주문 금액 검증
        //BigDecimal로 처리
        if(totalPrice.compareTo(BigDecimal.valueOf(shop.getMinimumPrice())) < 0){
            throw new IllegalArgumentException("최소 주문 금액을 만족하지 못했습니다.");
        }

        //가게 오픈/마감시간 확인
        LocalTime now = LocalTime.now();
        if(now.isBefore(shop.getOpenTime()) || now.isAfter(shop.getCloseTime())){
            throw new IllegalArgumentException("가게 영업 시간이 아닙니다.");
        }

        //주문 생성 및 저장
        Order order = new Order(customerId, menu, shop, requestDto.getQuantity(), requestDto.getAddress());
        orderRepository.save(order);

        //응답 dto 변환 후 반환
        return convertToResponseDto(order);
    }

    //주문 상태 변경 - 사장님
    @Transactional
    public OrderResponseDto updateOrderStatus(Long orderId, Long ownerId, OrderStatus newStatus){
        //주문 정보 확인
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        //해당 주문이 사장님의 가게인지 검증
        if(!order.getShop().getOwner().getId().equals(ownerId)){
            throw new IllegalArgumentException("해당 주문을 변경할 권한이 없습니다.");
        }

        //주문 상태 변경
        order.updateOrderStatus(newStatus);

        return convertToResponseDto(order);
    }

    //주문내역 조회 - 고객
    @Transactional
    public List<OrderResponseDto> getOrderByCustomer(Long customerId){

        return orderRepository.findByCustomerId(customerId).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    //주문내역 조회 - 가게
    @Transactional
    public List<OrderResponseDto> getOrderByShop(Long shopId, Long ownerId){
        ShopEntity shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new IllegalArgumentException("가게를 찾을 수 없습니다."));

        if(!shop.getOwner().getId().equals(ownerId)){
            throw new IllegalArgumentException("해당 가게의 주문을 조회할 권한이 없습니다.");
        }

        return orderRepository.findByShopId().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    //주문 데이터를 응답 dto로 변환
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
