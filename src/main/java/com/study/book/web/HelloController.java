package com.study.book.web;

import com.study.book.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// web package : 컨트롤러와 관련된 클래스를 담음
// @RestController : 컨트롤러를 JSON으로 반환하는 컨트롤러로 만들어줌 (data return이 주요 목적)
// @RestController VS @Controller => Controller는 view return이 주요 목적
@RestController
@RequestMapping(value = "/hello/**")
public class HelloController {

    @GetMapping("/")
    public String hello(){
        return "hello";
    }

    // @RequestParam : 외부에서 API로 넘긴 파라미터를 가져오는 어노테이션입니다.
    // 단, param으로 넘겨지는 값은 String만 허용됩니다.
    @GetMapping("dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name,
                                     @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount);
    }

    // 이때 view의 return 값은 sample만 출력되는데 그 이유는 이 controller는 RestController이기 때문
    @GetMapping("sample")
    public String sample() {
        return "sample";
    }

}


