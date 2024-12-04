package com.example.whattheeat.service;

import com.example.whattheeat.entity.ShopEntity;
import com.example.whattheeat.enums.State;
import com.example.whattheeat.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;

    @Transactional
    public ShopEntity createShop(ShopEntity shop) {
        try {
            return shopRepository.save(shop);
        } catch (DataIntegrityViolationException e) {
            log.error("가게생성실패: 중복된 이름-{}", shop.getName());
            throw new DataIntegrityViolationException("이미 등록된 가게입니다.");
        }

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
        //일단 가게 이름은 변경 못하는 걸로
        //shop.setName(updateShopEntity.getName());

        shop.setOpenTime(updateShopEntity.getOpenTime());
        shop.setCloseTime(updateShopEntity.getCloseTime());
        shop.setMinimumPrice(updateShopEntity.getMinimumPrice());
        shop.setState(updateShopEntity.getState());
        shop.setModifiedAt(LocalDateTime.now());

        return shopRepository.save(shop);
    }

    @Transactional(readOnly = true)
    public List<ShopEntity> getAllShops() {
        //  삭제 처리된 데이터까지 다 조회됨
        //  return shopRepository.findAll();
        return shopRepository.findByDeletedAtIsNull();
    }

    @Transactional(readOnly = true)
    public ShopEntity getShopById(Integer id) {
        return shopRepository.findById(id).orElseThrow(() -> new RuntimeException("Shop not found"));
    }


}
