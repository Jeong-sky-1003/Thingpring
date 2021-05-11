package com.study.book.web;

import com.study.book.service.posts.PostsService;
import com.study.book.web.dto.PostsResponseDTO;
import com.study.book.web.dto.PostsSaveRequestsDTO;
import com.study.book.web.dto.PostsUpdateRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

// @RequiredArgsConstructor : final 혹은 @NonNull 변수만 생성자의 파라메터로 받는 생성자를 생성함
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/**")
public class PostsApiController {

    private final PostsService postsService;

    // 게시물 저장
    @PostMapping("posts")
    public Long save(@RequestBody PostsSaveRequestsDTO requestsDTO) {
        return postsService.save(requestsDTO);
    }

    @GetMapping("{id}")
    public PostsResponseDTO findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    /*
        @PathVariable
        - Spring 3.0부터 추가된 기능
        - URL 경로에 변수명을 넣어줌
     */
    @PutMapping("{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDTO requestsDTO) {
        return postsService.update(id, requestsDTO);
    }

}
