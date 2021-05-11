package com.study.book.web.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/*
    @MappedSuperclass
    - JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우 필드들도 BaseTimeEntity의 필드도 Column으로 인식함

    @EntityListeners(AuditingEntityListener.class)
    - BaseTimeEntity 클래스에 Auditing 기능을 포함시킴
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {
    // Enntity들의 createdTime, modifiedDate를 자동으로 관리하는 역할

    // Entity가 생성되어 저장될 때, 시간이 자동 저장됨
    @CreatedDate
    private LocalDateTime createdDate;

    // 조회한 Entity 값을 변경할 때 시간이 자동 저장됨
    @LastModifiedDate
    private LocalDateTime modifiedTime;

}
