package com.beyond.basic.service;

import com.beyond.basic.domain.*;
import com.beyond.basic.repository.MyMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// input값의 검증 및 실질적인 비즈니스 로직은 서비스 계층에서 수행
@Service // 서비스 계층임을 표현함과 동시에 싱글톤객체로 생성
// Transactional 어노테이션을 통해 모든 메서드에 트랜잭션을 적용하고,
// 만약 예외가 발생 시 롤백처리 자동화 (각 메서드마다 하나의 트랜잭션으로 묶는다는 뜻) -> 하나라도 예외가 발생하면 모두 롤백처리
@Transactional //JPA에서 필수
public class MemberService {
    // 다형성 설계
    // 최초 MemberService가 만들어질때만 memerRepository가 만들어지도록 final 선언하여 재할당 불가하도록 함
    private final MyMemberRepository memberRepository;

    @Autowired // 싱글톤객체를 주입(Dependency Injection: DI) 받는다는 것을 의미
    public MemberService(MyMemberRepository memoryRepository){
        // MemberServive 생성자가 호출될 때마다 MemberRepository 객체 생성
        // 한 객체를 다른 클래스에서 갖다 쓸 수 있도록 하려면 SingleTon 사용 (객체 하나를 전역적을 접근하도록)
        this.memberRepository = memoryRepository; // 이름 충돌날 수 있으므로 this.붙여줌
    }

//    // 비다형성 설계
//    public class MemberService {
//        private final MyMemberRepository memberRepository;
//
//        @Autowired
//        public MemberService(MyMemberRepository memoryRepository){
//            this.memberRepository = memoryRepository;
//        }


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
        Member member = dto.toEntity(); // MemberReqDto 객체 메서드
        memberRepository.save(member); // 저장된 멤버
    }

    public MemberDetResDto memberDetail(Long id){
        // 사용자로부터 찾을 id받음
        Optional<Member> optMember = memberRepository.findById(id);

        // Optional로 리턴하는 이유 : 예외발생 시 트랜잭션 롤백시키기 위해서
        // 클라이언트에게 적절한 예외 메시지와 상태코드를 주는 것이 주요목적
        // 또한, 예외를 강제 발생시킴으로서 적절한 롤백처리 하는 것도 주요목적
        // 롤백처리 해주기 위해서는 @Transactional 어노테이션 사용해야함
        Member member = optMember.orElseThrow(()-> new EntityNotFoundException("없는 회원입니다."));

        MemberDetResDto memberDetResDto = member.detFromEntity();

        System.out.println("글쓴이의 쓴 글의 개수"+member.getPosts().size());
        for(Post p : member.getPosts()){
            System.out.println("글의 제목 "+p.getTitle());
        }
        return memberDetResDto;
    }

    public List<MemberResDto> memberList(){
        List<MemberResDto> memberResDtos = new ArrayList<>();
        List<Member> memberList = memberRepository.findAll();

        for(Member member : memberList){
//            MemberResDto dto = member.listFromEntity();
//            memberResDtos.add(dto);
            memberResDtos.add(member.listFromEntity());
        }
        return memberResDtos; // 멤버 호출
    }

    public void pwUpdate(MemberUpdateDto dto){
        Member member = memberRepository.findById(dto.getId()).orElseThrow(()-> new EntityNotFoundException("member is not found"));
        member.updatePw(dto.getPassword());
        // 기존 객체를 조회 후 수정한 다음에 save 시에는 JPA가 알아서 update로 실행 (새로운 객체 save로 인식 안함)
        // 추가, 수정이 모두 .save() -> 수정save로 쓰고 싶으면 findById로 조회 후 save
        memberRepository.save(member);
    }

    public void deleteMember(Long id){
        Member member = memberRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("this id is not found"));
        memberRepository.delete(member); // 완전삭제

//        member.updateDelYN("Y"); // 실제 개발할 때는 해당 유저의 데이터를 삭제하지 않고
//        memberRepository.save(member);
    }
}
