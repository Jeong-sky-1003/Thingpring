package com.study.book.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 글 수정 요청 DTO
// DB Layer와 Viewer Layer를 구분한 이 설계는 Adapter Pattern 인가?
@Getter
@NoArgsConstructor
public class PostsUpdateRequestDTO {

    private String title;
    private String content;

    @Builder
    public PostsUpdateRequestDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
