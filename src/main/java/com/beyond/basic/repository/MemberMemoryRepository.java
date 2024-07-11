package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

// 해당 클래스가 Repository 계층임을 표현함과 동시에 싱글톤 객체로 생성
// 싱글톤 : 모든 클래스에서 이 객체를 돌려쓸 수 있도록 만든다는 뜻 (단하나의 객체만 만든다.)
// new 할 필요없이 실행할 때 스프링에서 알아서 객체 생성한다. new 또 써버리면 또 객체 생성된다.
@Repository
public class MemberMemoryRepository implements MemberRepository{
    private final List<Member> memberList;

    MemberMemoryRepository(){
        this.memberList = new ArrayList<>();
    }

    @Override
    public Member save(Member member) {
         memberList.add(member);
         return member;
    }

    @Override
    public List<Member> findAll() {
        return memberList;
    }

    @Override
    public Member findById(Long id) {
        return null;
    }
}
