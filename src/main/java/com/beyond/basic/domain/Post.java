package com.beyond.basic.domain;

import lombok.Getter;

import javax.persistence.*;

//@Data
@Entity
@Getter
//@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

//    1:1의 경우 OneToOne

    @ManyToOne
    @JoinColumn(name = "member_id") // name : 매핑할 DB의 col이름, default : 매핑된속성명_id
    // Post 엔티티는 Member 엔티티와 n:1관계를 가짐.
    // @JoinColumn을 통해 Post의 member_id열이 Member의 id와 연결되어 있다.
    // JPA의 영속성(persistence) 컨텍스트에 의해 Member가 관리
    private Member member; // member객체를 갖다 쓰겠다 => 여러개의 Post가 한 Member에 매핑될 수 있음.

    public PostResDto fromEntity(){
        return new PostResDto(this.id, this.title);
    }

}
