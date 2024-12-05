package com.example.whattheeat.dto;

import com.example.whattheeat.enums.UserRole;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private Long Id;
    private String email;
    private String name;
    private String phoneNumber;
    private UserRole userRole;

    public UserResponseDto(Long id, String email, String name, String phoneNumber, UserRole userRole) {
        Id = id;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.userRole = userRole;
    }
}
