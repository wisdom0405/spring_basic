package com.beyond.basic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// MemberReqDto -> Member 객체로 만들어줘야 함
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberReqDto {
    private String name;
    private String email;
    private String password;

    // dto에서 entity로 변환
    // 추후에는 빌더패턴으로 변환
    public Member toEntity(){
        Member member = new Member(this.name, this.email, this.password);
        return member;
    }
}
