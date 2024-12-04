package com.example.whattheeat.service;

import com.example.whattheeat.entity.ShopEntity;
import com.example.whattheeat.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;

    @Transactional
    public ShopEntity createShop(ShopEntity shop){
        return shopRepository.save(shop);
    }
}
