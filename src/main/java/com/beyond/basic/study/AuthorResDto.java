package com.beyond.basic.study;

import lombok.Data;

@Data
public class AuthorResDto {
    // 서버가 사용자에게 데이터 응답줄 때 : 비밀번호는 보여주지 X
    private Long id;
    private String name;
    private String email;
}
