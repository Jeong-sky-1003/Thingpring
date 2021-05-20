package com.study.book.web;

import com.study.book.config.auth.dto.SessionUser;
import com.study.book.service.posts.PostsService;
import com.study.book.web.dto.PostsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping(value = "/")
    public String index(Model model){

        model.addAttribute("posts", postsService.findAllDesc());

        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null)
            model.addAttribute("userName", user.getName());

        return "index";

    }

    @GetMapping(value = "/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping(value = "posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDTO dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }

}
