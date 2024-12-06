package com.example.whattheeat.controller;

import com.example.whattheeat.dto.MenuRequestDto;
import com.example.whattheeat.dto.MenuResponseDto;
import com.example.whattheeat.dto.MenuUpdateResponseDto;
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
    @PostMapping("/shop/{shopId}")
    public ResponseEntity<MenuResponseDto> createMenu(
            @PathVariable Long shopId,
            @RequestBody MenuRequestDto dto,
            @SessionAttribute("authenticatedUserId") Long userId) {

        //메뉴 생성하고, 서비스 실행
        //메뉴 생성 service
        MenuResponseDto menuResponseDto = menuService.createMenu(userId,shopId,dto.getName(),dto.getPrice());
        //dto 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(menuResponseDto);
    }
    //메뉴 수정
    @PutMapping("/{menuId}")
    public ResponseEntity<MenuUpdateResponseDto> updateMenu(
            @PathVariable Long shopId,
            @PathVariable Long menuId,
            @RequestBody MenuRequestDto dto,
            @SessionAttribute("authenticatedUserId") Long userId) {

        //메뉴 수정 service 실행
        MenuUpdateResponseDto menuResponseDto = menuService.updateMenu(userId,shopId,menuId,dto.getName(),dto.getPrice());
        //dto로 반환
        return ResponseEntity.ok(menuResponseDto);
    }

    //메뉴 삭제
    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> deleteMenu(
            @PathVariable Long shopId,
            @PathVariable Long menuId,
            @SessionAttribute("authenticatedUserId") Long userId) {

        menuService.deleteMenu(userId,shopId,menuId);
        return ResponseEntity.noContent().build();
    }
}
