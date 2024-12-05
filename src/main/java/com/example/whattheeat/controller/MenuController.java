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

    public ResponseEntity<MenuResponseDto> createMenu(@PathVariable int shopId, @RequestBody MenuRequestDto dto,
                                                      HttpServletRequest request) {
        //세션에서 로그인된 사용자 정보 불러오기
        int userId = getUserId(request);
        //메뉴 생성하고, 서비스 실행

        //메뉴 생성 service
        MenuResponseDto menuResponseDto = menuService.createMenu(userId,shopId,dto.getName(),dto.getPrice());
        //dto 반환
        return new ResponseEntity<>(menuResponseDto, HttpStatus.CREATED);
    }
    //메뉴 수정
    @PutMapping("/{menuId}")
    public ResponseEntity<MenuUpdateResponseDto> updateMenu(@PathVariable int shopId, @PathVariable int menuId,
    @RequestBody MenuRequestDto dto, HttpServletRequest request) {
        //세션에서 로그인된 사용자 정보 가져오기
        int userId = getUserId(request);
        //메뉴 수정 service 실행
        MenuUpdateResponseDto menuResponseDto = menuService.updateMenu(userId,shopId,menuId,dto.getName(),dto.getPrice());
        //dto로 반혼
        return new ResponseEntity<>(menuResponseDto, HttpStatus.OK);
    }
    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> deleteMenu(@PathVariable int shopId,@PathVariable int menuId, HttpServletRequest request) {
        //세션에서 로그인된 사용자 정보 가져오기
        int userId = getUserId(request);
        menuService.deleteMenu(userId,shopId,menuId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    private static int getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        int userId = (int)session.getAttribute("LOGIN_USER");
        return userId;
    }

}
