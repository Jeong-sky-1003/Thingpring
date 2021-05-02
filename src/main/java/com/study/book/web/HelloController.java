package com.study.book.web;

import com.study.book.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// web package : 컨트롤러와 관련된 클래스를 담음
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/")
    public String hello(){
        return "hello";
    }

    // @RequestParam : 외부에서 API로 넘긴 파라미터를 가져오는 어노테이션입니다.
    // 단, param으로 넘겨지는 값은 String만 허용됩니다.
    @GetMapping("/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name,
                                     @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount);
    }

}


