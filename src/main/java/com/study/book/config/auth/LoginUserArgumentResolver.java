package com.study.book.config.auth;

import com.study.book.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

/*
    파라미터를 판단하기 위한 ArgumentResolver 를 생성했다고 바로 적용되는 것은 아님
    스프링에서 인식될 수 있도록 WebMvcConfigurer 를 추가해야 함
 */
@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    /*
        HandlerMethodArgumentResolver
        - 조건에 맞는 경우 메소드가 존재한다면 HandlerMethodArgumentResolver 의 구현체가 지정한 값으로
        - 해당 메소드의 파라미터로 넘길 수 있음
     */
    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        /*
            supportsParameter(MethodParameter parameter)
            - 컨트롤러 메서드의 특정 파라미터를 지원하는지 판단함
         */

        // 파라미터에 @LoginUser 어노테이션이 붙어있는지 확인
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        // 파라미터 클래스 타입이 SessionUser 인지 확인
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

        return isLoginUserAnnotation&&isUserClass;

    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer
                                    , NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        /*
            resolveArgument
            - 파라미터에 전달할 객체 생성
            - 우리는 로그인한 유저 정보를 보내기 위한것으로 세션의 객체를 반환함
         */
        return httpSession.getAttribute("user");
    }

}
