package com.example.whattheeat.service;

import com.example.whattheeat.dto.OrderRequestDto;
import com.example.whattheeat.dto.OrderResponseDto;
import com.example.whattheeat.entity.Menu;
import com.example.whattheeat.entity.Order;
import com.example.whattheeat.entity.Shop;
import com.example.whattheeat.entity.User;
import com.example.whattheeat.enums.OrderStatus;
import com.example.whattheeat.enums.ShopState;
import com.example.whattheeat.exception.CustomException;
import com.example.whattheeat.repository.MenuRepository;
import com.example.whattheeat.repository.OrderRepository;
import com.example.whattheeat.repository.ShopRepository;
import com.example.whattheeat.repository.UserRepository;
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
    private final UserRepository userRepository;

    //주문하기 - 고객 권한
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto, Long customerId){
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        //메뉴와 가게 존재 확인
        Menu menu = menuRepository.findById(requestDto.getMenuId())
                .orElseThrow(() -> new IllegalArgumentException("해당 메뉴를 찾을 수 없습니다."));

        Shop shop = shopRepository.findById(requestDto.getShopId())
                .orElseThrow(() -> new IllegalArgumentException("가게를 찾을 수 없습니다."));
        //삭제된 메뉴, 가게 확인
        if(shop.getDeletedAt() != null || !shop.getState().equals(ShopState.OPEN)){
            throw new IllegalArgumentException("주문할 수 없는 가게입니다.");
        }
        if(menu.getIsDeleted() != null){
            throw new IllegalArgumentException("주문할 수 없는 메뉴입니다.");
        }

        //메뉴와 가게의 연관관계
        if(!menu.getShop().getId().equals(requestDto.getShopId())){
            throw new IllegalArgumentException("메뉴가 해당 가게에 속하지 않습니다.");
        }

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
        Order order = new Order(customer, menu, shop, requestDto.getQuantity(), requestDto.getAddress());
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
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new IllegalArgumentException("가게를 찾을 수 없습니다."));

        if(!shop.getOwner().getId().equals(ownerId)){
            throw new IllegalArgumentException("해당 가게의 주문을 조회할 권한이 없습니다.");
        }

        return orderRepository.findByShopId(shopId).stream()
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
