package com.study.book.web.dto;

import com.study.book.web.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDTO {

    // 해당 DTO는 Entity의 필드 중 일부만 사용해 Entity를 받아 필드에 값을 넣음
    // 굳이 모든 필드를 가진 생성자가 필요하지 않아 Entity를 받아 처리함
    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsResponseDTO(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }

}
