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

    @Transactional
    public ShopResponseDto createShop(ShopRequestDto shopRequestDto, Long userId) {
        try {
            //사용자 조회
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // 운영 중인 가게 수 확인
            Long activeShopsCount = shopRepository.countActiveShopsByUserId(userId);
            if (activeShopsCount >= MAX_SHOP_COUNT) {
                throw new IllegalArgumentException("사장님은 최대 " + MAX_SHOP_COUNT + "개의 가게만 운영할 수 있습니다.");
            }

            Shop shop = Shop.builder()
                    .name(shopRequestDto.getName())
                    .user(user)
                    .openTime(shopRequestDto.getOpenTime())
                    .closeTime(shopRequestDto.getCloseTime())
                    .minimumPrice(shopRequestDto.getMinimumPrice())
                    .state(ShopState.OPEN)
                    .build();
            return ShopResponseDto.from(shopRepository.save(shop));
        } catch (DataIntegrityViolationException e) {
            log.error("가게생성실패: 중복된 이름-{}", shopRequestDto.getName());
            throw new DataIntegrityViolationException("이미 등록된 가게입니다.");
        }

    }


    @Transactional
    public void deleteShop(Integer id ,Long userId) {
        Shop shopEntity = shopRepository.findById(id).orElseThrow(() -> new RuntimeException("Shop not found"));

        shopEntity.setDeletedAt(LocalDateTime.now());
        shopEntity.setState(ShopState.CLOSED);  // 상태를 CLOSED로 변경
        shopRepository.save(shopEntity);
    }

    @Transactional
    public ShopResponseDto updateShop(Integer id, ShopRequestDto shopRequestDto) {
        Shop shop = shopRepository.findById(id).orElseThrow(() -> new RuntimeException("shop not found"));

        //수정할 내용
        //일단 가게 이름은 변경 못하는 걸로
        //shop.setName(updateShopEntity.getName());

        shop.setOpenTime(shopRequestDto.getOpenTime());
        shop.setCloseTime(shopRequestDto.getCloseTime());
        shop.setMinimumPrice(shopRequestDto.getMinimumPrice());
        shop.setState(ShopState.valueOf(shopRequestDto.getState()));

        return ShopResponseDto.from( shopRepository.save(shop));
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
    public ShopResponseDto getShopById(Integer id) {
        Shop shop = shopRepository.findByIdWithMenus(id).orElseThrow(() -> new RuntimeException("Shop not found"));

        List<MenuResponseDto> menus = shop.getMenus().stream()
                .map(menu -> new MenuResponseDto(
                        menu.getId(),
                        menu.getName(),
                        menu.getPrice()
                        //    menu.getMenuStatus()
                ))
                .collect(Collectors.toList());
        if (menus.isEmpty()) {
            throw new CustomException("메뉴가 준비중입니다. 죄송합니다.");
        }
        return new ShopResponseDto(
                shop.getId(),
                shop.getName(),
                shop.getOpenTime(),
                shop.getCloseTime(),
                shop.getMinimumPrice(),
                shop.getState().toString(),
                menus);
    }

    @Transactional(readOnly = true)
    public List<ShopResponseDto> getRandomShops() {
        // return shopRepository.findRandomShops();
        return shopRepository.findRandomShopsSelectColum();
    }


}
