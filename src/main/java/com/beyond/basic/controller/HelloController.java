package com.beyond.basic.controller;

import com.beyond.basic.domain.Hello;
import com.beyond.basic.domain.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/hello") // 클래스 차원에 url매핑시에 RequestMapping 사용
//

//@RestController // Controller + 각 메서드마다 @ResponseBody
// 해당 클래스가 컨트롤러임을 명시
// 사용자의 요청을 처리하고 응답하는 편의기능
public class HelloController{

//    case1. 사용자가 서버에게 화면요청 (get)
//    case2. @ResponseBody가 붙은경우 단순 String 데이터 return (get)

    //    getmapping을 통해 get 요청을 처리하고 url 패턴을 명시
    @GetMapping("/") // ("/hello")와 동일

//    ResponseBody를 사용하면 화면이 아닌 데이터를 return
//    만약 여기서 ResponseBody가 없고 return이 String 이면
//    스프링은 templates 폴더 밑에 helloworld.html화면을 찾아 리턴
    @ResponseBody
    public String helloWorld() {
        return "helloworld";
    }


    //    case3. 사용자가 json 데이터 요청 (get)
//    data를 리턴하되, json 형식으로 return
//    method 명은 helloJson, url 패턴은 /hello/json
    @GetMapping("/json")
    @RequestMapping(value = "/json", method = RequestMethod.GET) // 메서드 차원에서도 RequestMapping 사용가능
    @ResponseBody
//    responsebody를 사용하면서 객체를 return시 자동으로 직렬화
    public Hello helloJson() throws JsonProcessingException {
        Hello hello = new Hello();
        hello.setName("kim");
        hello.setEmail("kim@naver.com");
//        ObjectMapper objectMapper = new ObjectMapper();
//        String value = objectMapper.writeValueAsString(hello);
//        return value;
        return hello;
    }


    //    case4. 사용자가 json 데이터를 요청하되, parameter 형식으로 특정객체 요청
    // get 요청 중에 특정 데이터를 요청
    @GetMapping("/param1")
    @ResponseBody
//    parameter 형식 : ?name=hongildong (이 패턴대로 요청)
//    localhost:8080/hello/param1?name=hongildong
//    => hello 객체 찾아서 반환

//    inputName에 해당하는 "name"의 key값 찾아옴
    public Hello Param1(@RequestParam(value = "name") String inputName) {
        Hello hello = new Hello();
        hello.setName(inputName);
        hello.setEmail("hongildong@naver.com");
        System.out.println(hello); // Hello(name=hongildong, email=hongildong@naver.com, password=null)
        return hello;
    }

    // url 패턴 : Param2, 메서드명 : modelParam2
    // parameter 2개 : name, email => hello 객체 생성 후 리턴
    // 요청방식 : ?name=xxx&email=xxx@naver.com
    // RequestParam : 무조건 넣어줘야 함
    // localhost:8080/hello/param2?name=wisdom&email=wisdom@naver.com

    @GetMapping("/param2")
    @ResponseBody
    public Hello Param2(@RequestParam(value = "name") String inputName,
                        @RequestParam(value = "email") String inputEmail
    ) {
        Hello hello = new Hello();
        hello.setName(inputName);
        hello.setEmail(inputEmail);
        return hello;
    }

    //    case5. parameter 형식으로 요청하되, 서버에서 데이터바인딩 하는 형식
    @GetMapping("/param3")
    @ResponseBody
    // parameter가 많을 경우 객체로 대체가 가능하다.
    // 객체에 각 변수에 맞게 알아서 데이터 바인딩된다.(매핑된다.) => 변수명 일치해야 한다.
    // 데이터 바인딩 조건 : 기본생성자, setter
    // -> @NoArgsConstructor 있어야 함에 주의 (기본생성자 생성)
    // ?name=xxx&email=xxx@naver.com&password=xxx

    // http://localhost:8080/hello/model-param3?name=wisdom2&email=wisdom2@naver.com&password=1234
    public Hello Param3(Hello hello) {
        return hello; // 사용자가 넘겨오는 값 알아서 객체로 만들어서 넘겨줌
    }

    //    case6. 서버에서 화면에 데이터를 넣어 사용자에게 return (model 객체 사용) : 사용자의 input 데이터를 화면에 꽂아서 return한다.
//    파라미터 방식 : ?name=hongildong
//    @RestController : Controller + ResponseBody(데이터 return)
//    http://localhost:8080/hello/model-param?name=hongildong
    @GetMapping("/model-param")
    public String modelParam(@RequestParam(value = "name") String inputName, Model model) {
        // model 객체에 name이라는 key값에 value를 세팅하면 key:value는 화면으로 전달
        model.addAttribute("name", inputName);
        return "helloworld"; // helloworld.html 파일에서 name 변수를 사용가능하다.
    }

    //    case7. pathvariable방식을 통해 사용자로부터 값을 받아 화면 리턴
//    localhost:8080/hello/model-path/hongildong 패턴: hongildong 데이터 사용자 get
//    localhost:8080/author/1/name : id 1번 사용자의 name
//    pathvariable 방식은 url을 통해 자원의 구조를 명확하게 표현함으로, 좀 더 restful한 방식
//    restful : http 통신 원칙 중 하나. 자원의 계층 구조를 명확하게 표현
    @GetMapping("/model-path/{name}")
    public String modelPath(@PathVariable String inputName, Model model) {
        model.addAttribute("name", inputName);
        return "helloworld";
    }

    @GetMapping("/form-view")
//    사용자에게 name, email, password를 입력할 수 있는 화면을 주는 메서드 정의
//    메서드명 : formView, 화면명 : form-view
    public String formView() {
        return "form-view";
    }

    //    post 요청 (사용자 입장에서 서버에 데이터를 주는 상황)
//    case 1. url 인코딩 방식 (text만) 전송
//    input 데이터 형식 : key1=value1&key2=value&key3=value3
//    data가 key-value 형식이므로 RequestParam 써야함
//    localhost:8080/hello/form-view
    @PostMapping("/form-post1") // getmapping과 같은 url 패턴 사용 가능
    @ResponseBody
//    html의 name = "email"의 name과 매핑
    public String formPost1(@RequestParam(value = "name") String inputName,
                            @RequestParam(value = "email") String inputEmail,
                            @RequestParam(value = "password") String inputPassword
    ) {
        // "ok" return : 데이터 전송이므로 ResponseBody 사용
        // 사용자로부터 받아온 내용 출력
        System.out.println(inputName);
        System.out.println(inputEmail);
        System.out.println(inputPassword);
        return "ok";
    }

    @PostMapping("/form-post2")
    @ResponseBody
    public String formPost1(@ModelAttribute Hello hello) {
        // ModelAttribute는 생략가능 (데이터 바인딩을 하고 있다는 것을 명시하는 용도)
        System.out.println(hello);
        return "ok";
    }

    //    case2. multipart/form-data 방식(text와 file) 전송
//    url 명 : form-file-post3, 메서드명 : formFilePost3, 화면명 : form-file-view
//    form 태그 : name, email, password, file
    @GetMapping("/form-file-post")
    public String formFilePost() {
        return "form-file-view";
    }

    @PostMapping("/form-file-post")
    @ResponseBody
    public String formFileHandle(Hello hello,
                                 @RequestParam(value = "file") MultipartFile file
    ) {
        // 'Hello' 객체를 'formFileHandle' 메서드의 파라미터로 사용한 이유는
        // Spring MVC의 데이터 바인딩 기능을 이용하여
        // HTML 폼에서 전송된 데이터를 객체에 자동으로 매핑하기 위함입니다. 이는 코드의 간결성과 유지보수성을 높이는 데 유리합니다.
        // 이를 통해 여러개의 @RequestParam 어노테이션을 사용하는 대신, 하나의 객체를 사용하여 데이터를 받을 수 있습니다.
        System.out.println(hello);
        System.out.println(file.getOriginalFilename());
        return "ok";
    }

    // case3. js를 활용한 form 데이터 전송 (text만)
    @GetMapping("/axios-form-view")
    public String axiosFormView() {
//        name, email, password를 전송
        return "axios-form-view";
    }

    @PostMapping("axios-form-view")
    @ResponseBody
//    axios를 통해 넘어오는 형식이 key1=value1&key2=value2 등 url 인코딩방식
    public String axiosFormPost(@ModelAttribute Hello hello) {
        System.out.println(hello); // Hello(name=정슬기, email=letmegohome@naver.com, password=1234)
        return "ok";
    }

    // case4. js를 활용한 form 데이터 전송 (+file)
    @GetMapping("axios-form-file-view")
    public String axiosFormFileView() {
        return "axios-form-file-view";
    }

    @PostMapping("axios-form-file-view")
    @ResponseBody
    public String axiosFormFileViewPost(Hello hello,
                                        @RequestParam(value = "file") MultipartFile file
    ) {
        System.out.println(hello);
        System.out.println(file.getOriginalFilename());
        return "ok";
    }

    // case5. js를 활용한 json 데이터 전송
    // 이름, 이메일, 패스워드
    // url패턴 : axios-json-view, 화면명 : axios-json-view, get요청메서드 : 동일,
    // post요청 메서드 : axiosJsonPost
    @GetMapping("/axios-json-view")
    public String axiosJsonView() {
        return "axios-json-view";
    }

    // http://localhost:8080/hello/axios-json-view
    @PostMapping("/axios-json-view")
    @ResponseBody
//    parameter로 데이터를 전달하지 않아 Hello(name=null, email=null, password=null) 출력됨
//    json으로 전송한 데이터를 받을 때에는 @RequestBody 어노테이션 사용 : json 데이터를 parsing해서 객체로 만든다.
//    json으로 들어온 데이터를 처리할 때는 @RequestBody 어노테이션 사용, parameter 방식은 안붙여도 됨.
    public String axiosJsonPost(@RequestBody Hello hello) {
//        @RequestBody : 요청본문(request body)에 담긴 값을 자바객체로 변환
//        @RequestBody 어노테이션은 HTTP 요청 본문을 읽고 이를 지정된 클래스(Hello)의 객체로 변환합니다.
//        요청본문 : POST,PUT 요청에서 사용되며 json, xml, html, 폼데이터 등 다양한 형식으로 존재
//
        System.out.println(hello); // Hello(name=정슬기, email=wis@naver.com, password=1111)
        return "ok";
    }

    // case6. js를 활용한 json 데이터 전송 (json+file)
    @GetMapping("/axios-json-file-view")
    public String axiosJsonFileView(Hello hello) {
//        json은 문자형태이므로 json에 file을 끼워넣을 수 없다.
//        파일처리하려면 FormData 객체를 써야하나 json과 함께 쓰고싶은 경우 new Blob
//        hello 문자열 변수, file 변수

        return "axios-json-file-view";
    }

    @PostMapping("/axios-json-file-view")
    @ResponseBody
//    RequestPart는 파일과 Json을 처리할 때 주로 사용하는 어노테이션
    public String axiosJsonFilePost(
//                                    @RequestParam("hello") String hello,
//                                    @RequestParam("file") MultipartFile file
//                                     formdata를 통해 json, file(멀티미디어)을 처리할 때 RequestPart 어노테이션을 많이 사용
            @RequestPart("helloKey") Hello hello,
            @RequestPart("fileKey") MultipartFile file

    ) throws JsonProcessingException {
        System.out.println(hello);
        //Stiring으로 받은 뒤 수동으로 객체 변환
//        ObjectMapper objectMapper = new ObjectMapper();
//        Hello h1 = objectMapper.readValue(hello, Hello.class);
//        System.out.println(h1.getName());
        System.out.println(file.getOriginalFilename());
        return "ok";
    }

    // case7. js를 활용한 json 데이터 전송 (+여러 file)
    @GetMapping("/axios-json-multi-file-view")
    public String axiosJsonMultiFileView(Hello hello) {
//        json은 문자형태이므로 json에 file을 끼워넣을 수 없다.
//        파일처리하려면 FormData 객체를 써야하나 json과 함께 쓰고싶은 경우 new Blob
//        hello 문자열 변수, file 변수
        return "axios-json-multi-file-view";
    }

    @PostMapping("/axios-json-multi-file-view")
    @ResponseBody
//    RequestPart는 파일과 Json을 처리할 때 주로 사용하는 어노테이션
    public String axiosJsonMultiFilePost( @RequestPart("hello") Hello hello,
                                          @RequestPart("files") List<MultipartFile> files
    ){
        System.out.println(hello);
        for(MultipartFile file : files){
            System.out.println(file.getOriginalFilename());
        }
        return"ok";
    }

//    case8. 중첩된 json 데이터 처리
//    {name:'hongildong', email:'hong@naver.com', scores:[math:60, music:70, eng:100]};
    @GetMapping("/axios-nested-json-view")
    public String axiosNestedJsonView(){
        return "axios-nested-json-view";
    }

    @PostMapping("/axios-nested-json-view")
    @ResponseBody
    public String axiosNestedJsonPost(@RequestBody Student student){
        System.out.println(student);
        return "ok";
    }

}

