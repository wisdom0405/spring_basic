package com.beyond.basic.study;

import com.beyond.basic.domain.Hello;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hello") // 클래스 자원에 url 매핑시에 RequestMapping 사용
public class HelloControllerStudy {
    // @GetMapping : 클라이언트가 서버에서 데이터를 조회할 때 사용
    // 데이터 조회 : 서버에서 데이터를 가져오는데 사용. 서버의 상태나 데이터를 변경하지 않음

    //@PostMapping : 클라이언트가 서버에 데이터를 제출하거나, 서버의 상태를 변경하는 작업을 수행할 때 사용
    // 데이터 전송 : 서버에 데이터를 전송하는 데 사용. 폼 데이터를 제출하거나 파일을 업로드할 때 사용됨.

    // post 요청
    // case 1. url 인코딩 방식 (text만) 전송
    // input 데이터 형식 : key1=value1&key2=value2&key3=value3
    //    data가 key-value 형식이므로 RequestParam 써야함
    //    localhost:8080/hello/form-view

    @GetMapping("/form-view")
    // 사용자에게 name, email, password를 입력할 수 있는 화면을 주는 메서드 정의
    // 메서드명 : formView, 화면 명 : form-view
    public String formView(){
        return "form-view";
    }
    @PostMapping("/form-post1")
    @ResponseBody
    public String formPost(@RequestParam(value = "name")String inputName,
                           @RequestParam(value = "email")String inputEmail,
                           @RequestParam(value = "password")String inputPassword
                           ){
        System.out.println(inputName);
        System.out.println(inputEmail);
        System.out.println(inputPassword);
        return "ok";
    }

    // case3. js를 활용한 form 데이터 전송(text만)
    @GetMapping("/axios-form-view")
    public String axiosFormView(){
        return "axios-form-view";
    }
    @PostMapping("/axios-form-view")
    public String axiosFormPost(Hello hello){
        System.out.println(hello);
        return "ok";
    }



}
