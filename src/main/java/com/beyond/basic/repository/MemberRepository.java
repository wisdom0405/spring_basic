package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;

import java.util.List;

public interface MemberRepository {
    // 인터페이스이므로 실질적인 로직은 안들어감 -> Implements 해줘야 함.
    // return : Member 객체 (객체가 잘 저장됐다면 해당 Memeber객체 다시 return -> 최종적으로 controller, 사용자에게 알려주기 위해서)
    void save(Member memeber);

    List<Member> findAll();

    Member findById(Long id);
}
