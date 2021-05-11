package com.study.book.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/sample/**")
public class SampleController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("test", "test value");
        return "sample";
    }

}
