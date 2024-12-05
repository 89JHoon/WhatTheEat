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
    @PostMapping

    public ResponseEntity<MenuResponseDto> createMenu(@RequestBody MenuRequestDto dto,
                                                      HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        //메뉴 생성 service
        MenuResponseDto menuResponseDto = menuService.createMenu(dto);

        return new ResponseEntity<>(menuResponseDto, HttpStatus.CREATED); //dto로 반환
    }
    //메뉴 수정
    @PutMapping("/{menuId}")
    public ResponseEntity<MenuUpdateResponseDto> updateMenu(@RequestBody MenuRequestDto dto,
                                                      HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        //메뉴 수정 service
        MenuUpdateResponseDto menuResponseDto = menuService.updateMenu(dto);
        //dto로 반혼
        return new ResponseEntity<>(menuResponseDto, HttpStatus.OK);
    }
    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> deleteMenu(@PathVariable("menuId") int menuId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        menuService.deleteMenu(menuId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
