package com.example.whattheeat.service;

import com.example.whattheeat.config.PasswordEncoder;
import com.example.whattheeat.dto.UserRequestDto;
import com.example.whattheeat.dto.UserResponseDto;
import com.example.whattheeat.entity.User;
import com.example.whattheeat.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원 가입
    public UserResponseDto signUp(@Valid UserRequestDto requestDto) {
        checkDuplicateEmail(requestDto);
        User user = getUser(requestDto);
        User saveUser = userRepository.save(user);

        return new UserResponseDto(saveUser.getId(), saveUser.getEmail(), saveUser.getName(), saveUser.getPhoneNumber(),saveUser.getUserRole());
    }

    // 이메일 중복 체크
    private void checkDuplicateEmail(UserRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "중복된 이메일입니다");
        }
    }

    // db에 넣을 유저 객체 생성
    private User getUser(UserRequestDto requestDto) {
        String encodePassword = passwordEncoder.encode(requestDto.getPassword());

        return User.builder()
                .email(requestDto.getEmail())
                .password(encodePassword)
                .name(requestDto.getName())
                .phoneNumber(requestDto.getPhoneNumber())
                .userRole(requestDto.getUserRole())
                .build();
    }
}
