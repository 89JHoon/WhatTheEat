package com.example.whattheeat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {

    // 생성일
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createAt;

    // 수정일
    @LastModifiedDate
    @Column
    private LocalDateTime modifiedAt;

    // 삭제 시간
    // soft delete 시 값을 추가
    @Column
    private LocalDateTime deletedAt;
}
