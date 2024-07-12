package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// 해당 레파지토리를 mybatis 기술을 쓰겠다라는 표현
@Mapper
public interface MemberMybatisRepository extends MemberRepository{
//    MemberRepository 인터페이스 상속 받음
//    오버라이딩 굳이 할 필요없으므로 코드 따로 추가 작성안해도 됨.
}
