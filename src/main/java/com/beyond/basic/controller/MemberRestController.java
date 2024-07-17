package com.beyond.basic.controller;

import com.beyond.basic.domain.*;
import com.beyond.basic.service.MemberService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController // RestController의 경우 모든 메서드 상단에 @ResponseBody가 붙는 효과 발생
@RequestMapping("/rest")
@Api(tags = "회원관리 서비스")
public class MemberRestController {

    private final MemberService memberService;


    @Autowired
    public MemberRestController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("/member/text")
    public String memberText(){
        return"ok";
    }

//    member/create : 성공하면 200, 실패하면 400(HttpStatus.BAD_REQUEST)
    @PostMapping("/member/create")
    public ResponseEntity<Object> memberCreatePost(@RequestBody MemberReqDto dto){
        try{
            memberService.memberCreate(dto);
            // body에 들어가는 HttpStatus 상태
            CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED,"memberCreate 성공",null);
            return new ResponseEntity<>(commonResDto, HttpStatus.CREATED); // header에 들어가는 HttpStatus

        }catch(IllegalArgumentException e){
            e.printStackTrace();
            CommonErrorDto commonErrorDto = new CommonErrorDto(HttpStatus.BAD_REQUEST.value(),e.getMessage());
            return new ResponseEntity<>(commonErrorDto, HttpStatus.BAD_REQUEST);
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
    // memberList : CommonResDto 감싸서 상태코드까지 reuturn : 200
    @GetMapping("/member/list")
    public List<MemberResDto> memberList() {
        List<MemberResDto> memberList = memberService.memberList();
        return memberList;
    }

    //    회원상세조회 : memberDetail
    // memberDetail : 성공하면 CommonResDto(200),
    // 예외터지면 CommonErrorDto로 return(404, NOT_FOUND) = 자원없음
    @GetMapping("/member/detail/{id}")
    public ResponseEntity<Object> memberDetail(@PathVariable Long id) {
        try{
            MemberDetResDto memberDetResDto = memberService.memberDetail(id);
            CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "member를 찾았습니다.",memberDetResDto);
            return new ResponseEntity<>(commonResDto, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            CommonErrorDto commonErrorDto = new CommonErrorDto(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return new ResponseEntity<>(commonErrorDto,HttpStatus.NOT_FOUND);
        }
    }
}