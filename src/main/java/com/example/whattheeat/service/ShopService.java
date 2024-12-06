package com.example.whattheeat.service;

import com.example.whattheeat.dto.MenuResponseDto;
import com.example.whattheeat.dto.ShopResponseDto;
import com.example.whattheeat.dto.ShopRequestDto;
import com.example.whattheeat.entity.Shop;
import com.example.whattheeat.entity.User;
import com.example.whattheeat.enums.ShopState;
import com.example.whattheeat.exception.CustomException;
import com.example.whattheeat.repository.ShopRepository;
import com.example.whattheeat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private static final int MAX_SHOP_COUNT = 3;

    //가게 생성
    @Transactional
    public ShopResponseDto createShop(ShopRequestDto shopRequestDto, Long userId) {
        //사용자 조회
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User not found"));

        //가게 이름 중복 확인
        if(shopRepository.existsByName(shopRequestDto.getName())) {
            throw new CustomException("이미 등록된 가게 이름입니다.");
        }

        // 운영 중인 가게 수 확인
        Long activeShopsCount = shopRepository.countActiveShopsByUserId(userId);
        if (activeShopsCount >= MAX_SHOP_COUNT) {
            throw new IllegalArgumentException("사장님은 최대 " + MAX_SHOP_COUNT + "개의 가게만 운영할 수 있습니다.");
        }

        Shop shop = Shop.builder()
                .name(shopRequestDto.getName())
                .owner(owner)
                .openTime(shopRequestDto.getOpenTime())
                .closeTime(shopRequestDto.getCloseTime())
                .minimumPrice(shopRequestDto.getMinimumPrice())
                .state(ShopState.OPEN)
                .build();

        return ShopResponseDto.from(shopRepository.save(shop));
    }

    //가게 폐업
    @Transactional
    public void deleteShop(Long id ,Long userId) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new CustomException("Shop not found"));

        if(!shop.getOwner().getId().equals(userId)){
            throw new IllegalArgumentException("가게 폐업 권한이 없습니다.");
        }

        shop.closeShop();
    }

    //가게 수정
    @Transactional
    public ShopResponseDto updateShop(Long id, ShopRequestDto shopRequestDto, Long userId) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new CustomException("shop not found"));

        if(!shop.getOwner().getId().equals(userId)){
            throw new IllegalArgumentException("가게 수정 권한이 없습니다.");
        }
        //수정할 내용
        //일단 가게 이름은 변경 못하는 걸로
        //shop.setName(updateShopEntity.getName());
        shop.updateShopDetails(
                shopRequestDto.getOpenTime(),
                shopRequestDto.getCloseTime(),
                shopRequestDto.getMinimumPrice(),
                ShopState.valueOf(shopRequestDto.getState())
        );

        return ShopResponseDto.from(shop);
    }

    // 필요하지 않는 부분
//    @Transactional(readOnly = true)
//    public List<ShopResponseDto> getAllShops() {
//        //  삭제 처리된 데이터까지 다 조회됨
//        //  return shopRepository.findAll();
//        //  재오픈 하면 어쩔껀데?
//        //return shopRepository.findByDeletedAtIsNull();
//        return shopRepository.findByState(ShopState.OPEN).stream()
//                .map(ShopResponseDto::from)
//                .collect(Collectors.toList());
//    }

    @Transactional(readOnly = true)
    public ShopResponseDto getShopById(Long id) {
        Shop shop = shopRepository.findByIdWithMenus(id)
                .orElseThrow(() -> new RuntimeException("Shop not found"));

        return ShopResponseDto.from(shop);
    }

    @Transactional(readOnly = true)
    public List<ShopResponseDto> getRandomShops() {
        // return shopRepository.findRandomShops();
       //todo - shop을 shopResponseDto로 치환
        return null;
    }
}