package com.example.whattheeat.controller;

import com.example.whattheeat.constant.Const;
import com.example.whattheeat.dto.LoginRequestDto;
import com.example.whattheeat.dto.UserRequestDto;
import com.example.whattheeat.dto.UserResponseDto;
import com.example.whattheeat.dto.WithdrawRequestDto;
import com.example.whattheeat.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> singUp(@Valid @RequestBody UserRequestDto requestDto) {

        UserResponseDto userResponseDto = userService.signUp(requestDto);

        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    // 회원 탈퇴
    @PostMapping
    public ResponseEntity<String> withdraw(
            @Valid @RequestBody WithdrawRequestDto requestDto,
            @SessionAttribute(name = Const.LOGIN_USER) Long userId) {

        userService.withdraw(userId, requestDto);

        return new ResponseEntity<>("회원 탈퇴가 되었습니다.", HttpStatus.OK);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody LoginRequestDto requestDto,
            HttpServletRequest servletRequest) {

        Long loginId = userService.login(requestDto);
        setSession(servletRequest, loginId);

        return new ResponseEntity<>("로그인이 되었습니다.", HttpStatus.OK);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            HttpServletRequest servletRequest){

        sessionValidate(servletRequest);
        return new ResponseEntity<>("로그아웃 되었습니다.", HttpStatus.OK);
    }

    private static void sessionValidate(HttpServletRequest servletRequest) {
        HttpSession loginSession = servletRequest.getSession(false);
        if (loginSession != null) {
            loginSession.invalidate();
        }
    }

    private void setSession(HttpServletRequest servletRequest, Long loginId) {
        HttpSession session = servletRequest.getSession();
        session.setAttribute(Const.LOGIN_USER, loginId);
    }
}

