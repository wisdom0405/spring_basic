package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;

import java.util.List;

public class MemberJpaRepository implements MemberRepository{
    @Override
    public Member save(Member memeber) {
        return null;
    }

    @Override
    public List<Member> findAll() {
        return List.of();
    }

    @Override
    public Member findById(Long id) {
        return null;
    }
}
