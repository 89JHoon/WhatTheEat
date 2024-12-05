package com.example.whattheeat.dto.common;

import com.example.whattheeat.enums.UserRole;
import lombok.Getter;

@Getter
public class Authentication {

    private Long Id;

    private UserRole userRole;

    public Authentication(Long id, UserRole userRole) {
        Id = id;
        this.userRole = userRole;
    }
}
