package com.study.book.web;

import com.study.book.web.domain.posts.Posts;
import com.study.book.web.domain.posts.PostsRepository;
import com.study.book.web.dto.PostsSaveRequestsDTO;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/*
    @WebMvcTest를 사용하지 않는 이유?
        - @WebMvcTest : JPA 기능을 수행하지 않음

    JPA와 외부 연동 기능을 한 번에 테스트할 때, @SpringBootTest, TestRestTemplate 사용
 */
/*
    @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
        - 랜덤 포트 실행
        - TestRestTemplate 를 주입받아 테스트 함
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    // @LocalServerPort : 실제 주입되는 포트번호 확인 가능
    @LocalServerPort
    private int port;

    // HttpMethod
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_등록된다() throws Exception {

        // given
        String title = "title";
        String content = "content";
        System.out.println("port: " + port);

        PostsSaveRequestsDTO requestsDTO = PostsSaveRequestsDTO.builder()
                                                                .title(title)
                                                                .content(content)
                                                                .author("sky")
                                                                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";
        System.out.println("url: " + url);

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestsDTO, Long.class);

        // then
        // 200번대 성공 여부
        System.out.println("responseEntity.getBoty(): " + responseEntity.getBody());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);

    }

}
