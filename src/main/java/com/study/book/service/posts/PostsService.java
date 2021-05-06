package com.study.book.service.posts;

import com.study.book.web.domain.posts.PostsRepository;
import com.study.book.web.dto.PostsResponseDTO;
import com.study.book.web.dto.PostsSaveRequestsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/*
    @RequiredArgsConstructor
    - @Autowired는 권장되지 않음
    - 해당 어노테이션을 통해 생성자를 생성해 @Autowired와 동일한 효과
 */
@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    public Long save(PostsSaveRequestsDTO requestsDTO) {
        return postsRepository.save(requestsDTO.toEntity()).getId();
    }

    public PostsResponseDTO findById(Long id) {
        return postsRepository.findById(id);
    }
}
