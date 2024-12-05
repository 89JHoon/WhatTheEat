package com.example.whattheeat.controller;

import com.example.whattheeat.dto.MenuRequestDto;
import com.example.whattheeat.dto.MenuResponseDto;
import com.example.whattheeat.dto.UpdateMenuRequestDto;
import com.example.whattheeat.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menus")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;


    //메뉴 생성
    @PostMapping

    public ResponseEntity<MenuResponseDto> createMenu(@PathVariable int shopId,@RequestBody MenuRequestDto dto,
                                                      HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        MenuResponseDto menuResponseDto = menuService.createMenu(shopId,dto.getName(),dto.getPrice());

        return new ResponseEntity<>(menuResponseDto, HttpStatus.CREATED);
    }
    //메뉴 수정
    @PutMapping("/{menuId}")
    public ResponseEntity<MenuResponseDto> updateMenu(@ModelAttribute UpdateMenuRequestDto requestDto){
        MenuResponseDto menuResponseDto = menuService.updateMenu(
                requestDto.getShopId(),
                requestDto.getName(),
                requestDto.getPrice()

        );
        return new ResponseEntity<>(menuResponseDto, HttpStatus.OK);
    }
}
