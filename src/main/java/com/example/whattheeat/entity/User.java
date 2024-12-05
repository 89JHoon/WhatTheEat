package com.example.whattheeat.entity;

import com.example.whattheeat.enums.UserRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@Table
@SQLDelete(sql = "update user set deleted = true  where id = ?")
@SQLRestriction("deleted = false")
@NoArgsConstructor
public class User extends BaseEntity{

    // 유저 ID
    // 기본 키 설정, 자동 생성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 유저 이메일
    @Column(nullable = false, length = 50)
    private String email;

    // 유저 비밀번호
    @Setter
    @Column(nullable = false, length = 100)
    private String password;

    // 유저 이름
    @Column(nullable = false, length = 50)
    private String name;

    // 유저 핸드폰 번호
    @Setter
    @Column(nullable = false, length = 50)
    private String phoneNumber;

    // 유저 역할
    // USER, OWNER
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    // 회원 상태
    @Column(nullable = false)
    private Boolean deleted = Boolean.FALSE;

    @Builder
    public User(String email, String password, String name, String phoneNumber, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.userRole = userRole;
    }
}
