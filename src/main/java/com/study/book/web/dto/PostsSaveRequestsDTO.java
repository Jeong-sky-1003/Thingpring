package com.study.book.web.dto;

import com.study.book.web.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@NoArgsConstructor
@Getter
public class PostsSaveRequestsDTO {

    private String title;
    private String content;
    private String author;

    /*
        저장 요청 객체와 DB에서 불러오는 객체 분리. 허나 규칙이 있음.
        Entity 클래스를 Request/Response 클래스로 사용해서는 안 됨

        Entity 클래스는 DB와 맞닿은 핵심 클래스로, 이를 기준으로 테이블이 생성되며 스키마가 변경됨
        반면에 Request 와 Response 용 DTO는 View를 위한 클래스로 자주 변경이 필요함

        그렇기에 View Layer와 DB Layer 역할 분리를 철저하게 하는 것이 좋음
        Controller에서 결괏값으로 여러 테이블을 조인해줘야 하는 일이 발생하기 때문도 있음
     */
    @Builder
    public PostsSaveRequestsDTO(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity(){
        return Posts.builder()
                    .title(title)
                    .content(content)
                    .author(author)
                    .build();
    }

}
