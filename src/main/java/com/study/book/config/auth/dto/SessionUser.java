package com.study.book.config.auth.dto;

import com.study.book.web.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

// SessionUser 클래스는 인증된 사용자 정보만 갖기 위함
// Serializable :
@Getter
public class SessionUser implements Serializable {
    /*
        User를 사용하지 않는 이유는?
        - User 클래스는 Entity 클래스임
        - Entity 클래스는 언제 다른 Entity와 관계가 형성될지 모름
        - 만약 관계가 맺어있는 클래스가 존재한다면, 자식 클래스까지 직렬화 대상에 포함됨
        - 이로 성능 이슈, 부수 효과가 발생할 확률이 높음
        - 고로 직렬화 기능을 가진 세션 DTO를 하나 추가로 만드는 것이 유지보수를 위해 좋음
     */

    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }

}
