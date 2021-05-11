package com.study.book.web.domain.posts;

import com.study.book.web.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor  // 매개변수를 갖지 않는 생성자 생성
@Entity             // 이를 선언함으로 반드시 기본키를 의미하는 어노테이션을 작성해야 함
                    // 테이블과 링크될 클래스임을 나타냄 snake_naming
public class Posts extends BaseTimeEntity {

    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 규칙을 나타내며, Auto Increment 적용
    private Long id;

    // 최대 길이는 500, null 값 비허용
    @Column(length = 500, nullable = false)
    private String title;

    // 기본값 TEXT, null 값 비허용
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    // 글이 수정될 경우 DB의 내용 수정하기 위함
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
