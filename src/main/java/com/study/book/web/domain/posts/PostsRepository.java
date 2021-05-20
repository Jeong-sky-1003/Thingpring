package com.study.book.web.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// DB 접근을 위한 JPA Repository
public interface PostsRepository extends JpaRepository<Posts, Long> {

    // JPQL로 조회 : 오름차순 조회
    /*

        규모가 있는 프로젝트에서 데이터 조회는 외래키(FK)로 복잡해 조회용 프레임워크를 추가로 사용
        ex. querydsl, jooq, MyBatis 등
        등록/수정/삭제 --> SpringDataJpa

        querydsl 추천 이유

        (1) 타입 안정성 보장
        - 메소드 기반으로 쿼리를 생성해 오타 혹은 존재하지 않는 컬럼명일 경우 IDE에서 자동 검출

        (2) 국내 많은 회세아서 사 용
        - JPA를 적극 사용하는 회사에서 Querydsl 적극 사용

        (3) 레퍼런스 많음
        - 많이 사용되어 국내 자료가 많음

     */
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();

}


