package com.example.whattheeat.controller;

import com.example.whattheeat.entity.ShopEntity;
import com.example.whattheeat.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shops")
public class ShopController {

    @Autowired
    private final ShopService shopService;

    // 가게 등록
    @PostMapping
    public ResponseEntity<ShopEntity> createShop(@RequestBody ShopEntity shop){

        return  ResponseEntity.ok(shopService.createShop(shop));
    }

    //가게 삭제(폐업)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShop(@PathVariable Integer id){
        shopService.deleteShop(id);
        return ResponseEntity.noContent().build();
    }




}
