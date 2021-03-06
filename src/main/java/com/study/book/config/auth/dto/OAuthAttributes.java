package com.study.book.config.auth.dto;

import com.study.book.web.domain.user.Role;
import com.study.book.web.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name,
                           String email, String picture) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
    }

    // OAuth2User에서 반환하는 정보는 Map이기에 값 하나하나를 변환해야 함
    public static OAuthAttributes of(String registrationId, String userNameAttributeName,
                                     Map<String, Object> attributes) {

        if ( registrationId.equals("naver") )
            return ofNaver("id", attributes);

        return ofGoogle(userNameAttributeName, attributes);

    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                    .name((String) attributes.get("name"))
                    .email((String) attributes.get("email"))
                    .picture((String) attributes.get("picture"))
                    .attributes(attributes)
                    .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {

        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                    .name((String) response.get("name"))
                    .email((String) response.get("email"))
                    .picture((String) response.get("profile_image"))
                    .attributes(response)
                    .nameAttributeKey(userNameAttributeName)
                .build();
    }

    /*
        - User Entity 생성
        - 엔티티 생성 시점은 처음 가입할 때
        - 가입 기본 권한을 GUEST로 주기 위해 Role.GUEST 사용
        - OAuthAttributes 클래스 생성 종료시, 같은 패키지에 SessionUser 클래스 생성
     */
    public User toEntity() {
        return User.builder()
                    .name(name)
                    .email(email)
                    .picture(picture)
                    .role(Role.GUEST)
                .build();
    }

}
