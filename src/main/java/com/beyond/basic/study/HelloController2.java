package com.beyond.basic.study;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/study")
public class HelloController2 {

    // ResponseBody : 사용하는 화면이 아닌 데이터를 return,
    // ResponseBody가 없으면 'helloworld.html'화면 찾아서 리턴
    @GetMapping("/")
    @ResponseBody
    public String helloWord(){
        return "helloworld";
    }

    // url 패스변수 : url 경로의 일부로 전달되는 값을 의미
    // 패스변수는 경로의 특정위치에 값을 포함시켜 전달할 수 있으며, @PathVariable 사용하여 컨트롤러 메서드에서 받을 수 있다.

    // case 3. 사용자가 json 데이터 요청(post)


}
