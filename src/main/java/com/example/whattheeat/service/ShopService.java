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

    @Transactional
    public ShopEntity updateShop(Integer id, ShopEntity updateShopEntity) {
        ShopEntity shop = shopRepository.findById(id).orElseThrow(() -> new RuntimeException("shop not found"));

        //수정할 내용
        shop.setName(updateShopEntity.getName());
        shop.setOpenTime(updateShopEntity.getOpenTime());
        shop.setCloseTime(updateShopEntity.getCloseTime());
        shop.setMinimumPrice(updateShopEntity.getMinimumPrice());
        shop.setState(updateShopEntity.getState());
        shop.setModifiedAt(LocalDateTime.now());

        return shopRepository.save(shop);
    }
}
