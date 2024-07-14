package com.beyond.basic.study;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service // 서비스 계층임을 표현함과 동시에 싱글톤 객체로 생성
@Transactional // 만약 예외발생 시 롤백처리 자동화
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired // 생성자방식으로 DI의존성 주입
    public AuthorService (AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    public void authorCreate(AuthorReqDto dto){
        if(dto.getPassword().length()<4){
            throw new IllegalArgumentException("비밀번호가 너무 짧습니다.");
        }
        Author author = new Author();
        author.setName(dto.getName());
        author.setEmail(dto.getEmail());
        author.setPassword(dto.getPassword());
        authorRepository.save(author); // 저장된 멤버
    }

    public AuthorDetResDto authorDetail(Long id){
        Optional<Author> optAuthor = authorRepository.findById(id);
        AuthorDetResDto authorDetResDto = new AuthorDetResDto();

        Author author = optAuthor.orElseThrow(()->new EntityNotFoundException("없는 회원입니다."));
        authorDetResDto.setId(author.getId());
        authorDetResDto.setName(author.getName());
        authorDetResDto.setEmail(author.getEmail());
        authorDetResDto.setPassword(author.getPassword());

        return authorDetResDto;
    }

    public List<AuthorResDto> authorList(){
        List<AuthorResDto> authorResDtos = new ArrayList<>();
        List<Author> authorList = authorRepository.findAll();

        for(Author author : authorList){
            AuthorResDto dto = new AuthorResDto();
            dto.setId(author.getId());
            dto.setName(author.getName());
            dto.setEmail(author.getEmail());
            authorResDtos.add(dto);
        }
        return authorResDtos;
    }
}
