package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberJpaRepository implements MemberRepository{
//    EntityManager는 JPA의 핵심클래스(객체)
//    Entity의 생명주기를 관리, 데이터베이스와의 모든 인터페이싱을 책임
//    즉, 엔티티를 대상으로 CRUD 하는 기능을 제공
//
    @Autowired
    private EntityManager entityManager;

    @Override
    public Member save(Member member) {
//        persist : 전달된 엔티티(Member)가 EntityManager의 관리상태가 되도록 만들어주고,
//        트랜잭션이 커밋될 때 데이터베이스에 저장. insert.
        entityManager.persist(member);
        return null;
    }

    // 여러가지 요소 가져오므로 .getResultList()
    @Override
    public List<Member> findAll() {
//        jpql : jpa의 raw쿼리 문법(객체지향 쿼리문법)
//        jpa에서는 jpql 문법 컴파일에러가 나오지 않으나, springdatajpa에서는 컴파일 에러 발생
        List<Member> memberList = entityManager.createQuery("select m from Member m", Member.class).getResultList();
        return memberList;
    }

//    // ? 아니고 :email 이런식 (email은 unique한 요소이므로 .getSingleResult)
//    public Member findByEmail(String email){
//        Member member = entityManager.createQuery("select m from Member m where m.email= :email"
//                        , Member.class)
//                        .setParameter("email",email).getSingleResult();
//    }

    @Override
    public Optional<Member> findById(Long id) {
//        entitymanager를 통해 find한다.(리턴타입 클래스 지정 및 매개변수로 pk 필요)
        Member member = entityManager.find(Member.class, id); // 리턴타입 : member
        return Optional.ofNullable(member);
    }

    // pk 이외의 컬럼으로 조회할 때 (수동으로 쿼리 짜야함)
    // jpql문법으로 raw쿼리 비슷하게 직접 쿼리 작성

}
