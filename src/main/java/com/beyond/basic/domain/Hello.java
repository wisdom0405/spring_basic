package com.beyond.basic.domain;

import lombok.*;

// lombok 라이브러리를 통해 getter, setter 어노테이션 사용한다.
//@Getter
//@Setter
@Data // getter, setter, toString 등을 포함
//@NoArgsConstructor // 기본 생성자 만드는 어노테이션
//@AllArgsConstructor // 모든 매개변수를 사용한 생성자를 만드는 어노테이션
public class Hello {
    private String name;
    private String email;
    private String password;


//    @Override
//    public String toString(){
//        return "이름은 : " + this.name + " email은 " + this.email;
//    }

//    Data toString 어노테이션 사용하면 터미널에 아래와 같이 출력됨.
//    http://localhost:8080/hello/model-param1?name=hongildong
//    Hello(name=hongildong, email=hongildong@naver.com, password=null)



}
