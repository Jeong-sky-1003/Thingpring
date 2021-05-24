package com.study.book;

import com.study.book.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.study.book.web.HelloController;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class,
            excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
            }
)
public class HelloControllerTest {

    // MVC 테스트의 시작점으로 HTTP Method API를 테스트할 수 있음
    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(roles = "USER")
    public void hello가_리턴된n() throws Exception {

        String hello = "hello";

        System.out.println("test 시작");

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));

    }

    @Test
    @WithMockUser(roles = "USER")
    public void helloDTO_리턴() throws Exception {

        String name = "hello";
        int amount = 1000;

        // perform 메서드로 URI를 지정하며 param으로 parameter 변수명과 값을 할당함
        // jsonPath JSON 응답값을 필드별로 검증할 수 있는 메서드로, $를 기준으로 필드명을 명시함
        mvc.perform( get("/hello/dto")
                    .param("name", name)
                    .param("amount", String.valueOf(amount)))

                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is(name)))
                    .andExpect(jsonPath("$.amount", is(amount)));

    }

}


