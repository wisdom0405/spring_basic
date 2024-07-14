package com.beyond.basic.study;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
//    // Author 객체 생성 후 저장
//    Author save(Author author);
//    // Author 리스트
//    List<Author> findAll();
//    // id기반으로 Author 찾아줌 (JpaRepository 인터페이스와 싱크 맞춰주기 위해서 optional로 선언)
//    Optional<Author> findById(Long id);
}
