package com.beyond.basic.service;

import com.beyond.basic.controller.MemberController;
import com.beyond.basic.domain.Member;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.repository.MemberJdbcRepository;
import com.beyond.basic.repository.MemberMemoryRepository;
import com.beyond.basic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// input값의 검증 및 실질적인 비즈니스 로직은 서비스 계층에서 수행
@Service // 서비스 계층임을 표현함과 동시에 싱글톤객체로 생성
public class MemberService {
    // 최초 MemberService가 만들어질때만 memerRepository가 만들어지도록 final 선언하여 재할당 불가하도록 함
    private final MemberRepository memberRepository;

    @Autowired // 싱글톤객체를 주입(Dependency Injection: DI) 받는다는 것을 의미
    public MemberService(MemberJdbcRepository memoryRepository){
        // MemberServive 생성자가 호출될 때마다 MemberRepository 객체 생성
        // 한 객체를 다른 클래스에서 갖다 쓸 수 있도록 하려면 SingleTon 사용 (객체 하나를 전역적을 접근하도록)
        this.memberRepository = memoryRepository; // 이름 충돌날 수 있으므로 this.붙여줌
    }

//    MemberService에서도 MemberController Autowired 해주면 순환참조 에러
//     컨트롤러 -> 서비스(참조) -> 컨트롤러(참조)
//    @Autowired
//    private MemberController memberController;

    public void memberCreate(MemberReqDto dto){
        // 인터페이스 = new 실제 해당클래스 객체
        // 사용자로부터 member객체 그대로 넘겨받음
        if(dto.getPassword().length()< 8){
            throw new IllegalArgumentException("비밀번호가 너무 짧습니다.");
        }
        Member member = new Member();
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        member.setPassword(dto.getPassword());
        memberRepository.save(member);
    }

    public Member memberDetail(Long id){

        return memberRepository.findById(id);
    }

    public List<MemberResDto> memberList(){
        List<MemberResDto> memberResDtos = new ArrayList<>();
        List<Member> memberList = memberRepository.findAll();

        for(Member member : memberList){
            MemberResDto dto = new MemberResDto();
            dto.setName(member.getName());
            dto.setEmail(member.getEmail());
            memberResDtos.add(dto);
        }
        return memberResDtos; // 멤버 호출
    }
}
