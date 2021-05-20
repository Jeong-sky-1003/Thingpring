package com.study.book.web.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 이미 가입한 사용자인지 확인하기 위함
    Optional<User> findByEmail(String email);

}
