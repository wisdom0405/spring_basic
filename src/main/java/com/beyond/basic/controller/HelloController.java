package com.beyond.basic.controller;

import com.beyond.basic.domain.Hello;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/hello") // 클래스 차원에 url매핑시에 RequestMapping 사용

// 클래스와 메소드의 RequestMapping을 통해 URL을 매핑하여 경로를 설정하여 해당 메소드에서 처리
// value : url설정, method : GET, POST, DELETE, PUT, PATCH

//@RestController // Controller + 각 메서드마다 @ResponseBody
// 해당 클래스가 컨트롤러임을 명시
// 사용자의 요청을 처리하고 응답하는 편의기능
public class HelloController {

//    case1. 사용자가 서버에게 화면요청 (get)
//    case2. @ResponseBody가 붙은경우 단순 String 데이터 return (get)

//    getmapping을 통해 get 요청을 처리하고 url 패턴을 명시
    @GetMapping("/") // ("/hello")와 동일

//    ResponseBody를 사용하면 화면이 아닌 데이터를 return
//    만약 여기서 ResponseBody가 없고 return이 String 이면
//    스프링은 templates 폴더 밑에 helloworld.html화면을 찾아 리턴
    @ResponseBody
    public String helloWorld(){
        return "helloworld";
    }

    // 아래와 같이 Controller에서도 HttpServletRequest를 주입받아 사용 가능
//    public String helloWorld2(HttpServletRequest request){
//        System.out.println(request.getSession());
//        System.out.println(request.getHeader("Cookie"));
//        return "helloworld";
//    }


//    case3. 사용자가 json 데이터 요청 (get)
//    data를 리턴하되, json 형식으로 return
//    method 명은 helloJson, url 패턴은 /hello/json
    @GetMapping("/json")
    @RequestMapping(value = "/json", method = RequestMethod.GET) // 메서드 차원에서도 RequestMapping 사용가능
    @ResponseBody
//    responsebody를 사용하면서 객체를 return시 자동으로 직렬화
    public Hello helloJson() throws JsonProcessingException{
        Hello hello = new Hello();
        hello.setName("kim");
        hello.setEmail("kim@naver.com");
//        ObjectMapper objectMapper = new ObjectMapper();
//        String value = objectMapper.writeValueAsString(hello);
//        return value;
        return hello;
    }

    // @GetMapping(without Param) : 별도의 파라미터 없이 GET API를 호출하는 경우 사용되는 방법
    //    case4. 사용자가 json 데이터를 요청하되, parameter 형식으로 특정객체 요청

    // get 요청 중에 특정 데이터를 요청
    @GetMapping("/param1")
    @ResponseBody
//    parameter 형식 : ?name=hongildong (이 패턴대로 요청)
//    localhost:8080/hello/param1?name=hongildong
//    => hello 객체 찾아서 반환

//    inputName에 해당하는 "name"의 key값 찾아옴
    public Hello Param1(@RequestParam(value = "name")String inputName){
        Hello hello = new Hello();
        hello.setName(inputName);
        hello.setEmail("hongildong@naver.com");
        System.out.println(hello); // Hello(name=hongildong, email=hongildong@naver.com, password=null)
        return hello;
    }

    // url 패턴 : Param2, 메서드명 : Param2
    // parameter 2개 : name, email => hello 객체 생성 후 리턴
    // 요청방식 : ?name=xxx&email=xxx@naver.com
    // RequestParam : 무조건 넣어줘야 함
    // localhost:8080/hello/param2?name=wisdom&email=wisdom@naver.com

    @GetMapping("/param2")
    @ResponseBody
    public Hello Param2(@RequestParam(value = "name")String inputName,
                        @RequestParam(value = "email")String inputEmail
    ){
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
    public Hello Param3(Hello hello){
        return hello; // 사용자가 넘겨오는 값 알아서 객체로 만들어서 넘겨줌
    }

//    case6. 서버에서 화면에 데이터를 넣어 사용자에게 return (model 객체 사용) : 사용자의 input 데이터를 화면에 꽂아서 return한다.
//    파라미터 방식 : ?name=hongildong
//    @RestController : Controller + ResponseBody(데이터 return)
//    http://localhost:8080/hello/model-param?name=hongildong
    @GetMapping("/model-param")
    public String modelParam(@RequestParam(value = "name")String inputName, Model model){
        // model 객체에 name이라는 key값에 value를 세팅하면 key:value는 화면으로 전달
        model.addAttribute("name",inputName);
        return "helloworld"; // helloworld.html 파일에서 name 변수를 사용가능하다.
    }

//    case7. pathvariable방식을 통해 사용자로부터 값을 받아 화면 리턴
//    localhost:8080/hello/model-path/hongildong 패턴: hongildong 데이터 사용자 get
//    localhost:8080/author/1/name : id 1번 사용자의 name
//    pathvariable 방식은 url을 통해 자원의 구조를 명확하게 표현함으로, 좀 더 restful한 방식
//    restful : http 통신 원칙 중 하나. 자원의 계층 구조를 명확하게 표현
    @GetMapping("/model-path/{name}")
    public String modelPath(@PathVariable String inputName, Model model){
        model.addAttribute("name", inputName);
        return "helloworld";
    }

    @GetMapping("/form-view")
//    사용자에게 name, email, password를 입력할 수 있는 화면을 주는 메서드 정의
//    메서드명 : formView, 화면명 : form-view
    public String formView(){
        return "form-view";
    }

//    post 요청 (사용자 입장에서 서버에 데이터를 주는 상황)
//    PostAPI : 리소스를 추가하기 위해 사용하는 API : 일반적으로 추가하고자 하는 리소스를 http body에 추가하여 서버에 요청. 그렇기 때문에 @RequestBody를 이용하여 body에 담겨있는 값을 받아야 함.

//    case 1. url 인코딩 방식 (text만) 전송
//    input 데이터 형식 : key1=value1&key2=value&key3=value3
//    localhost:8080/hello/formView
    @PostMapping("/form-post1") // getmapping과 같은 url 패턴 사용 가능
    @ResponseBody
//    html의 name = "email"의 name과 매핑
    public String formPost1(@RequestParam(value = "name")String inputName,
                            @RequestParam(value = "email")String inputEmail,
                            @RequestParam(value = "password")String inputPassword
                            ){
        // "ok" return : 데이터 전송이므로 ResponseBody 사용
        // 사용자로부터 받아온 내용 출력
        System.out.println(inputName);
        System.out.println(inputEmail);
        System.out.println(inputPassword);
        return "ok";
    }

    @PostMapping("/form-post2")
    @ResponseBody
    public String formPost1(@ModelAttribute Hello hello){
        // ModelAttribute는 생략가능 (데이터 바인딩을 하고 있다는 것을 명시하는 용도)
        System.out.println(hello);
        return "ok";
    }

//    case2. multipart/form-data 방식(text와 file) 전송
//    url 명 : form-file-post3, 메서드명 : formFilePost3, 화면명 : form-file-view
//    form 태그 : name, email, password, file
    @GetMapping("/form-file-post")
    public String formFilePost(){
        return "form-file-view";
    }

    @PostMapping("/form-file-post")
    @ResponseBody
    public String formFileHandle(Hello hello,
                                 @RequestParam(value = "file") MultipartFile file
                                 ){
        System.out.println(hello);
        System.out.println(file.getOriginalFilename());
        return "ok";
    }

    // case3. js를 활용한 form 데이터 전송 (text만)
    @GetMapping("/axios-form-view")
    public String axiosFormView(){
//        name, email, password를 전송
        return "axios-form-view";
    }

    @PostMapping("axios-form-view")
    @ResponseBody
//    axios를 통해 넘어오는 형식이 key1=value1&key2=value2 등 url 인코딩방식
    public String axiosFormPost(@ModelAttribute Hello hello){
        System.out.println(hello); // Hello(name=정슬기, email=letmegohome@naver.com, password=1234)
        return "ok";
    }

    // case4. js를 활용한 form 데이터 전송 (+file)
    @GetMapping("axios-form-file-view")
    public String axiosFormFileView(){
        return "axios-form-file-view";
    }

    @PostMapping("axios-form-file-view")
    @ResponseBody
    public String axiosFormFileViewPost(Hello hello,
                                        @RequestParam(value = "file")MultipartFile file
                                        ){
        System.out.println(hello);
        System.out.println(file.getOriginalFilename());
        return "ok";
    }
    // case5. js를 활용한 json 데이터 전송
    // case6. js를 활용한 json 데이터 전송 (+file)
    // case7. js를 활용한 json 데이터 전송 (+여러 file)

    // formData를 통해 json, file(멀티미디어)을 처리할 때 RequestPart 어노테이션을 많이 사용한다.
    @PostMapping("/axios-json-file-view")
    @ResponseBody
    public String axiosJsonFilePost(@RequestPart("hello") Hello hello, @RequestPart("photo") MultipartFile photo) {
        System.out.println(hello);
        System.out.println(photo.getOriginalFilename());
        return "ok";
    }

    // 빌더패턴 실습
    public void helloBuilderTest(){
        Hello hello = Hello.builder().build();
    }


}

