package com.study.book.domain.posts;

import com.study.book.web.domain.posts.Posts;
import com.study.book.web.domain.posts.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

// assertThat 반드시 상속!
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    /*
        @After
        - Junit 단위 테스트가 끝날 때마다 수행되는 메소드를 지정
        - 배포 전 전체 테스트 수행 시, 테스트간 데이터 침범을 막기 위해 사용
        - 여러 테스트 동시 수행시, 테스트용 DB인 H2에 데이터가 그대로 남아
          다음 테스트 실행 시 테스트가 실패할 수 있어 선언
     */
    @After
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void BaseTimeEntity_등록() {

        // given
        LocalDateTime now = LocalDateTime.of(2021, 5, 7, 0, 0,0);
        postsRepository.save(Posts.builder()
                                .title("title")
                                .content("content")
                                .author("author")
                                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedTime()).isAfter(now);

    }

    // given-when-then 패턴
    @Test
    public void 게시글저장_불러오기(){

        // given - 할당 및 저장
        String title = "게시글 제목";
        String content = "게시글 본문";
        String author = "sky";
        Posts posts = Posts.builder().title(title)
                                        .content(content)
                                        .author(author)
                                        .build();
        postsRepository.save(posts);

        // when - 불러오기
        List<Posts> list = postsRepository.findAll();

        // then - 검사
        Posts post = list.get(0);

        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);

    }

}
