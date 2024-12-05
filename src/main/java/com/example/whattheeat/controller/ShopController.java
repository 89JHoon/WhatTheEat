package com.example.whattheeat.controller;

import com.example.whattheeat.dto.ShopDto;
import com.example.whattheeat.entity.ShopEntity;
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

    @Autowired
    private final ShopService shopService;

    // 가게 등록
    @PostMapping
    public ResponseEntity<ShopEntity> createShop(@RequestBody ShopEntity shopEntity){
        return  ResponseEntity.ok(shopService.createShop(shopEntity));
    }

    //가게 삭제(폐업)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShop(@PathVariable Integer id){
        shopService.deleteShop(id);
        return ResponseEntity.noContent().build();
    }


    //가게 정보 변경
    @PutMapping("/{id}")
    public ResponseEntity<ShopEntity> updateShop(@RequestBody ShopEntity shopEntity , @PathVariable Integer id){
        return ResponseEntity.ok(shopService.updateShop(id,shopEntity));
    }

    //가게 조회 (전체 조회)
    @GetMapping
    public ResponseEntity<List<ShopEntity>> getAllShops(){
        return ResponseEntity.ok(shopService.getAllShops());
    }

    //가게 조회(단건 조회)
    @GetMapping("/{id}")
    public ResponseEntity<ShopEntity> getShopById( @PathVariable  Integer id){
        return ResponseEntity.ok(shopService.getShopById(id));
    }

    //가게 조회(랜덤 조회)
    @GetMapping("/random")
    public ResponseEntity<List<ShopDto>> getRandomShops(){
        return ResponseEntity.ok(shopService.getRandomShops());
    }

}
