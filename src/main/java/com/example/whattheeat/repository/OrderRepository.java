package com.example.whattheeat.repository;

import com.example.whattheeat.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    //특정 고객의 주문내역 조회
    List<Order> findByCustomerId(Long customerId);

    //특정 가게의 주문내역 조회
    List<Order> findByShopId(Long shopId);
}
