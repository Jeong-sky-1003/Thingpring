package com.study.book.config.auth;

import com.study.book.config.auth.dto.OAuthAttributes;
import com.study.book.config.auth.dto.SessionUser;
import com.study.book.web.domain.user.User;
import com.study.book.web.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

// 구글 로그인 이후 가져온 정보를 기반으로 가입, 정보수정, 세션 저장 기능 지원
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User>
                delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // registrationId : 로그인 진행 중인 서비스 구분 (즉, 구글인지, 네이버인지)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        /*
            userNameAttributeName
            - OAuth2 로그인 진행 시 키가 되는 필드 값
            - primary key와 같은 의미
            - 구글은 기본적 코드를 지원하나, 카카오, 네이버 등은 기본 지원하지 않음
            - 구글 기본 코드: sub
         */
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                                        .getUserInfoEndpoint().getUserNameAttributeName();

        // oAuth2User.getAttributes()의 반환 값 : Map<String, Object>
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
                                                            oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        // SessionUser() : session에 사용자 정보를 저장하기 위함
        // 이는 로그인을 성공할 경우 세션에 값이 저장되도록 함
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                        Collections.singleton(new SimpleGrantedAuthority(
                        user.getRoleKey())),
                        attributes.getAttributes(), attributes.getNameAttributeKey());

    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                    .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                    .orElse(attributes.toEntity());
        System.out.println("save result: " + user.getName() + ", " + user.getEmail());
        return userRepository.save(user);
    }

}
