//package com.beyond.basic.study;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import java.util.List;
//
//@Controller // Controller임을 명시
//public class AuthorController {
//    private final AuthorService authorService;
//
//    @Autowired // 생성자 방식으로 의존성 주입
//    public AuthorController(AuthorService authorService){
//        this.authorService = authorService;
//    }
//
//    @GetMapping("/")
//    public String home(){
//        return "member/home";
//    }
//
//    @GetMapping("/member/create")
//    public String authorCreate(){
//        return "member/create";
//    }
//    @PostMapping("/member/post")
//    public String authorCreatePost(AuthorReqDto dto, Model model){
//        try{
//            authorService.authorCreate(dto);
//            return "/member/list";
//
//        }catch(IllegalArgumentException e){
//            model.addAttribute("error", e.getMessage());
//            return "/member/error";
//        }
//    }
//
//    @GetMapping("/member/list")
//    public String memberList(Model model){
//        List<AuthorResDto> authorResDtos = authorService.authorList();
//        model.addAttribute("memberList", authorResDtos);
//        return "/member/member-list";
//    }
//
//    @GetMapping("/member/detail/{id}")
//    public String memberDetail(@PathVariable Long id, Model model){
//        AuthorDetResDto authorDetResDto = authorService.authorDetail(id);
//        model.addAttribute("member", authorDetResDto);
//        return "/member/detail";
//    }
//
//
//}
