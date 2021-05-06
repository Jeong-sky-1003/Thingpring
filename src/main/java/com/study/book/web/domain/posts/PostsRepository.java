package com.study.book.web.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

// DB 접근을 위한 JPA Repository
public interface PostsRepository extends JpaRepository<Posts, Long> {

}


