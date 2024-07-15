package com.beyond.basic.controller;

import com.beyond.basic.domain.MemberDetResDto;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
// Controller : 싱글톤 @Component라고 붙어있음.
@Controller
//@RequiredArgsConstructor
public class MemberController {

    // 의존성 주입(DI) 방법1. 생성자주입방식 (가장 많이 사용하는 방식)
    // 장점 : 1) final을 통해 상수로 사용가능->재할당 불가(안정성) 2) 다형성 구현가능 3) 순환참조방지
    // 생성자가 1개 밖에 없을 때는 Autowired 생략가능
    private final MemberService memberService;

    // Spring IoC 컨테이너가 해당 타입의 빈을 찾아서 필드, 생성자, 메서드 파라미터 등에 자동으로 주입
    @Autowired //MemberService 싱글톤 객체를 Autowired를 통해 객체 주입받음
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

//    // 의존성 주입(DI) 방법2. 필드주입방식(Autowired만 사용)
//    @Autowired
//    private MemberService memberService;

    // 의존성 주입(DI) 방법3. 어노테이션(RequiredArgs)을 이용하는 방식
    // @RequiredArgsConstructor: @NonNull어노테이션, final 키워드가 붙어있는 필드를 대상으로 생성자 생성
//    @NonNull
//    private final MemberService memberService; // 반드시 초기화해야하는 변수 대상으로 기본생성자를 자동으로 만들어줌

    //localhost:8080
    @GetMapping("/")
    public String home(){
        return "member/home";
    }

    //    회원가입화면 주고
//    url : member/create
//    member-create
    @GetMapping("/member/create")
    public String memberCreate(){
        return "member/member-create";
    }

    //    회원가입데이터를 받는다
//    url : member/create
//    name, email, password
    @PostMapping("/member/create")
    public String memberCreatePost(MemberReqDto dto, Model model){
        try{
            memberService.memberCreate(dto);
//        회원가입 완료 후 회원목록조회 페이지로 리다이렉션 (url호출)
            return "redirect:/member/list"; // 화면리턴이 아닌 url 재호출

        }catch(IllegalArgumentException e){
            model.addAttribute("error",e.getMessage());
            return "member/member-error";
        }

//        System.out.println(member); // Member(name=정슬기, email=wisdom@naver.com, password=11111)
    }

    //    회원목록조회
    @GetMapping("/member/list")
    public String memberList(Model model) {
        List<MemberResDto> memberList = memberService.memberList();
        model.addAttribute("memberList", memberList);
        return "member/member-list";
    }

    //    회원상세조회 : memberDetail
//    url(uri) : member/1, member/2
//    화면명: member-detail
    @GetMapping("/member/detail/{id}")
//    int 또는 long 받을 경우 스프링에서 형변환(String->Long)
//    password까지 보여줌 -> MemberDetailResDto
    public String memberDetail(@PathVariable Long id, Model model) {
        MemberDetResDto memberDetResDto = memberService.memberDetail(id);
        model.addAttribute("member", memberDetResDto);
        return "member/member-detail";
    }


}