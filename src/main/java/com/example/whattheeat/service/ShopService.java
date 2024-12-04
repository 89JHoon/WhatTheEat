package com.example.whattheeat.service;

import com.example.whattheeat.entity.ShopEntity;
import com.example.whattheeat.enums.State;
import com.example.whattheeat.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;

    @Transactional
    public ShopEntity createShop(ShopEntity shop) {
        return shopRepository.save(shop);
    }


    @Transactional
    public void deleteShop(Integer id) {
        ShopEntity shopEntity = shopRepository.findById(id).orElseThrow(() -> new RuntimeException("Shop not found"));

        shopEntity.setDeletedAt(LocalDateTime.now());
        shopEntity.setState(State.CLOSED);  // 상태를 CLOSED로 변경
        shopRepository.save(shopEntity);
    }
}
