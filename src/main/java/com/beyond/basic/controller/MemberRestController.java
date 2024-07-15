package com.beyond.basic.controller;

import com.beyond.basic.domain.MemberDetResDto;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.domain.MemberUpdateDto;
import com.beyond.basic.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // RestController의 경우 모든 메서드 상단에 @ResponseBody가 붙는 효과 발생
@RequestMapping("/rest")
public class MemberRestController {

    private final MemberService memberService;


    @Autowired
    public MemberRestController(MemberService memberService){
        this.memberService = memberService;
    }

    @PostMapping("/member/create")
    public String memberCreatePost(@RequestBody MemberReqDto dto){
        try{
            memberService.memberCreate(dto);
            return "ok";

        }catch(IllegalArgumentException e){
            e.printStackTrace();
            return "error!!";
        }
    }

    // 수정 2가지 요청 방식 : PUT, PATCH (통신규약O 기능X)
    // patch요청은 부분수정, put요청은 덮어쓰기
    @PatchMapping("/member/pw/update")
    public String memberUpdate(@RequestBody MemberUpdateDto dto){
        memberService.pwUpdate(dto);
        return "ok";
    }

    @DeleteMapping("/member/delete/{id}")
    public String deleteMember(@PathVariable Long id){
        memberService.deleteMember(id);
        return "ok";
    }


    //    회원목록조회
    @GetMapping("/member/list")
    public List<MemberResDto> memberList() {
        List<MemberResDto> memberList = memberService.memberList();
        return memberList;
    }

    //    회원상세조회 : memberDetail
    @GetMapping("/member/detail/{id}")
    public MemberDetResDto memberDetail(@PathVariable Long id) {
        return memberService.memberDetail(id);
    }

}