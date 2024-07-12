package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// SpringDataJpaRepository(MemberRepository)가 되기 위해서는 JpaRepository를 상속해야 하고,
// 상속 시에 제네릭에 Entity명과 Entity의 PK타입을 명시해줘야 한다. JpaRepository<Entity,PK타입>
// MemberRepository는 JpaRepository를 상속함으로서 JpaRepository의 주요 기능을 상속하고 있다 => SpringDataJpaRepository
// JpaRepository가 인터페이스임에도 해당 기능을 상속해서 사용할 수 있는 이유는 hibernate 구현체가 미리 구현돼 있기 때문이다.
// 런타임 시점에 사용자가 인터페이스에 정의한 메서드를 프록시(대리인) 객체가 리플렉션 기술을 통해 메서드를 구현한다.
public interface MemberSpringDataJpaRepository extends MemberRepository, JpaRepository<Member, Long> {
// JpaRepository 인터페이스를 상속했는데 구현체가 없어도 사용가능 함 -> 구현체가 사용자에게 보이지는 않음 -> 런타임 시점에서 구현체가 주입된다. (hibernate)

    // 리플렉션을 통해 런타임 시점에 사용자가 정의한 메서드를 실시간으로 기능이 구현되기 때문에 어떤 요소가 들어와도 상관없음
//    Optional<Member> findByEmail(String email);
//    Optional<Member> findByEmailAndName(String email, String name); // 이런식으로 두개의 요소로도 찾을 수 있음 CamelCase 사용해야 함

}
