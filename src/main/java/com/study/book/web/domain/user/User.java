package com.study.book.web.domain.user;

import com.study.book.web.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    /*
        @Enumerated(EnumType.STRING)
        - JPA로 DB 저장을 할 때, Enum 값을 어떤 형태로 저장할지 결정
        - 기본적으로 int로 된 숫자가 저장됨
        - 숫자로 저장될 경우 코드의 의미를 파악할 수 없어 String으로 저장하도록 함
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }

}
