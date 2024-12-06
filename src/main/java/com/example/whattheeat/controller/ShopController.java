package com.example.whattheeat.controller;

import com.example.whattheeat.dto.ShopResponseDto;
import com.example.whattheeat.dto.ShopRequestDto;
import com.example.whattheeat.entity.Shop;
import com.example.whattheeat.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shops")
public class ShopController {

    private final ShopService shopService;

    // 가게 등록
    @PostMapping
    public ResponseEntity<ShopResponseDto> createShop(@RequestBody ShopRequestDto shopRequestDto,
                                                      @SessionAttribute("authenticatedUserId") Long userId
    ) {

        //사장님인지 검증
//        if(!"OWENR".equals(role)){
//            throw new IllegalArgumentException("사장님만 가게를 등록할 수 있습니다.");
//        }

        return ResponseEntity.ok(shopService.createShop(shopRequestDto, userId));
    }

    //가게 삭제(폐업)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShop(@PathVariable Integer id,
                                           @SessionAttribute("authenticatedUserId") Long userId

    ) {
        shopService.deleteShop(id, userId);
        return ResponseEntity.noContent().build();
    }


    //가게 정보 변경
    @PutMapping("/{id}")
    public ResponseEntity<ShopResponseDto> updateShop(@RequestBody ShopRequestDto shopRequestDto,
                                                      @PathVariable Integer id,
                                                      @SessionAttribute("authenticatedUserId") Long userId
                                                      // @SessionAttribute("authenticatedUserRole") String role
    ) {
        return ResponseEntity.ok(shopService.updateShop(id, shopRequestDto));
    }

    //가게 조회 (전체 조회)
    //일단 사용 x
//    @GetMapping
//    public ResponseEntity<List<ShopResponseDto>> getAllShops() {
//        return ResponseEntity.ok(shopService.getAllShops());
//    }

    //가게 조회(단건 조회)
    @GetMapping("/{id}")
    public ResponseEntity<ShopResponseDto> getShopById(@PathVariable Integer id) {
        return ResponseEntity.ok(shopService.getShopById(id));
    }

    //가게 조회(랜덤 조회)
    @GetMapping("/random")
    public ResponseEntity<List<ShopResponseDto>> getRandomShops() {
        return ResponseEntity.ok(shopService.getRandomShops());
    }


}
