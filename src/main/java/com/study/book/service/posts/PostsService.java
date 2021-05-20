package com.study.book.service.posts;

import com.study.book.web.domain.posts.Posts;
import com.study.book.web.domain.posts.PostsRepository;
import com.study.book.web.dto.PostsListResponseDto;
import com.study.book.web.dto.PostsResponseDTO;
import com.study.book.web.dto.PostsSaveRequestsDTO;
import com.study.book.web.dto.PostsUpdateRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/*
    @RequiredArgsConstructor
    - @Autowired는 권장되지 않음
    - 해당 어노테이션을 통해 생성자를 생성해 @Autowired와 동일한 효과
 */
@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    // readOnly=true : 트랜잭션 범위를 유지하되, 조회 기능만 남겨 조회 속도를 개선할 수 있음
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        // Steam map을 통해 PostsListResponseDto 로 변환함
        /*
            처음에 PostsListResponseDto 내에 지역 변수와 생성자를 정의하지 않았을 때 오류가 발생했으나
            선언 후 오류를 해결할 수 있었음
         */
        return postsRepository.findAllDesc().stream()
                                .map(PostsListResponseDto::new)
                                .collect(Collectors.toList());
    }

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

    @Transactional
    public void delete(Long id) {
        /*
            먼저 존재하는 Posts 확인 후 엔티 자체 삭제
            이거 말고도 deleteById(id)로 삭제할 수 있음
         */
        Posts posts = postsRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        postsRepository.delete(posts);
    }

}
