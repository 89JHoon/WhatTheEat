package com.example.whattheeat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass //직접 테이블로 매핑되지 않고, 상속받는 엔티티에 필드가 추가됨
@Getter
@EntityListeners(AuditingEntityListener.class) //JPA Auditing 기능 활성화
public class BaseEntity {

    //엔티티 생성 시 자동으로 저장되는 필드
    //필드는 생성 후 수정되지 않도록 설정, 반드시 값이 설정되어야 함
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    //엔티티 수정 시 자동으로 업데이트 되는 필드
    @LastModifiedDate
    @Column
    private LocalDateTime modifiedAt;

    //삭제 시간을 기록하는 필드
    //직접 값을 설정해야하고, soft delte를 구현할 때 사용
    @Column
    private LocalDateTime deletedAt;
}
