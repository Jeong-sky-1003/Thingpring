package com.study.book.service.posts;

import com.study.book.web.domain.posts.Posts;
import com.study.book.web.domain.posts.PostsRepository;
import com.study.book.web.dto.PostsResponseDTO;
import com.study.book.web.dto.PostsSaveRequestsDTO;
import com.study.book.web.dto.PostsUpdateRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Posts entity =  postsRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        return new PostsResponseDTO(entity);
    }

    /*
        update 메소드는 쿼리를 날리는 부분이 없음
        그 이유는 JPA의 영속성 컨텍스트 때문임
        영속성 컨텍스트 : 엔티티를 영구 저장하는 환경

        JPA 핵심 내용
            엔티티가 영속성 컨텍스트에 포함되어 있는가 아닌가

        JPA 의 엔티티 매니저가 활성하된 상태로 트랜잭션 안에서 DB에서 데이터를 가져오면 해당 데이터는 영속성 컨텍스트가 유지된 상태임
        이 상태에서 해당 데이터 값을 변경하면 트랜잭션이 끝나는 시점에 해당 테이블에 변경분을 반영함

        Entity의 값만 변경해 update 쿼리를 날릴 필요 없음. 이를 더티 체킹이라 함
        https://jojoldu.tistory.com/415
     */
    @Transactional
    public Long update(Long id, PostsUpdateRequestDTO requestDTO) {
        Posts posts =   postsRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        posts.update(requestDTO.getTitle(), requestDTO.getContent());
        return id;
    }

}
