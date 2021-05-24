package com.study.book.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// LoginUser 어노테이션 생성하기
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {

    /*
        @Target(ElementType.PARAMETER)
        - 어노테이션이 생성될 수 있는 위치 지정
        - 파라미터로 지정해 메소드의 파라미터로 선언된 객체에서만 사용 가능

        public @interface LoginUser
        - 어노테이션 클래스로 지정
        - LoginUser 어노테이션 생성
     */

}


