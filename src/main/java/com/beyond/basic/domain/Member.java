package com.beyond.basic.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
// Entity어노테이션을 통해 엔티티매니저에게 객체관리를 위임
// 해당 클래스명으로 테이블 및 컬럼을 자동생성하고, 각종 설정정보 위임
@Entity //JPA가 관리해야할 대상 엔티티임을 명시 (EntityManager)
//@AllArgsConstructor
@NoArgsConstructor // AllArgs 쓰려면 NoArgs도 써야됨 : 기본생성자는 JPA에서 필수, 기본생성자가 있어야 JPA가 객체 만들 수 있음.
public class Member{
//    name, email, password
    @Id //pk설정
    // identity : auto_increment 설정
    // auto : jpa 자동으로 적절한 전략을 선택하도록 맡기는 것
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment 설정
    private Long id; //Long은 biggint로 변환 (int는 int로 변환)
    // String은 Varchar(255)로 기본으로 변환. name 변수명이 name컬럼명으로 변환
    private String name;
    // nullable = false : notnull 제약조건, unique = true : unique 조건
    @Column(nullable = false, length=50, unique = true)
    private String email;
//    @Column(name = "pwd") 이렇게 할 수 있으나, 컬럼명과 변수명을 일치시키는 것이 혼선을 줄일 수 있음.
    private String password;

    // @OneToMany의 mappedBy 속성은 Post 엔티티의 member필드에 매핑된다는 것을 나타냄
    // 이는 Member엔티티가 관계의 주인이 아니라는 의미(FK를 관리하고 관계의 변경을 책임지는 엔티티)
    @OneToMany(mappedBy = "member") // 대문자 아님! Post엔티티의 member필드에 매핑됨을 나타냄.
    private List<Post> posts; // 한 Member가 여러 Post를 가질 수 있음

    // 변수명을 camelcase(passWord)로 할 경우 데이터베이스에서는 언더바형태(pass_word)로 들어감
    @CreationTimestamp // DB에는 current_timestamp가 생성되지 않음.
    private LocalDateTime createdTime; // 값을 만드는 최초에만 시각 들어감

    @UpdateTimestamp // 값을 수정할때마다 최근갱신시각 들어감
    private LocalDateTime updateTime;

    public Member(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // password 상단에 @Setter를 통해 특정 변수만 setter 사용이 가능하나,
    // 일반적으로 의도를 명확하게 한 메서드를 별도로 만들어 사용하는 것을 권장.
    public void updatePw(String password){
        this.password = password;
    }

    public MemberDetResDto detFromEntity(){
        LocalDateTime createdTime = this.getCreatedTime();
        String value = createdTime.getYear()+"년 "+createdTime.getMonthValue()+"월 "+createdTime.getDayOfMonth()+"일";

        return new MemberDetResDto(this.id, this.name, this.email, this.password, value);
    }

    public MemberResDto listFromEntity(){

        return new MemberResDto(this.id, this.name, this.email);
    }

}
