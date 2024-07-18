package com.beyond.basic.domain;

import lombok.*;

// lombok 라이브러리를 통해 getter, setter 어노테이션 사용한다.
//@Getter
//@Setter
@Data // getter, setter, toString 등을 포함
@NoArgsConstructor // 기본 생성자 만드는 어노테이션
@AllArgsConstructor // 모든 매개변수를 사용한 생성자를 만드는 어노테이션
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

    // Builder 패턴 직접 구현
    // Builder 적용 대상 생성자가 필요 (기본생성자)
    public Hello(HelloBuilder helloBuilder){
        this.name = helloBuilder.name;
        this.email = helloBuilder.email;
        this.password = helloBuilder.password;
    }

    public static HelloBuilder builder(){
        return new HelloBuilder();
    }

    // static 내부 클래스
    public static class HelloBuilder{
        private String name;
        private String email;
        private String password;

        public HelloBuilder name(String name){
            this.name = name;
            return this; //HelloBuilder 리턴함
        }

        public HelloBuilder email(String email){
            this.email = email;
            return this; //HelloBuilder 리턴함
        }

        public HelloBuilder password(String password){
            this.password = password;
            return this; //HelloBuilder 리턴함
        }

        public Hello build(){
            return new Hello(this);
        }

    }


}
