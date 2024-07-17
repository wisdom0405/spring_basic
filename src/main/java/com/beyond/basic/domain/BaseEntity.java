package com.beyond.basic.domain;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
// 이것만 가지고 객체를 만들지 못하도록 abstract class로 선언해줌
@Getter
// 기본적으로 Entity는 상속관계가 불가능하여, 해당 어노테이션을 붙여야 상속관계 성립 가능
@MappedSuperclass
public abstract class BaseEntity {
    @CreationTimestamp // DB에는 current_timestamp가 생성되지 않음.
    private LocalDateTime createdTime; // 값을 만드는 최초에만 시각 들어감

    @UpdateTimestamp // 값을 수정할때마다 최근갱신시각 들어감
    private LocalDateTime updateTime;
}
