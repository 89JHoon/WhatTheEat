package com.example.whattheeat.service;

import com.example.whattheeat.config.PasswordEncoder;
import com.example.whattheeat.dto.UserRequestDto;
import com.example.whattheeat.dto.UserResponseDto;
import com.example.whattheeat.dto.WithdrawRequestDto;
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

    public void withdraw(Long userId, WithdrawRequestDto requestDto) {
        User findUser = findUserById(userId);
        checkingPassword(requestDto, findUser);
        userRepository.delete(findUser);
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

    // 비밀번호 체크
    private void checkingPassword(WithdrawRequestDto requestDto, User findUser) {
        if (!passwordEncoder.matches(requestDto.getPassword(), findUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 맞지 않습니다");
        }
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유효하지 않는 ID입니다")
        );
    }
}
