package com.study.book.config.auth;

import com.study.book.web.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity  // 시큐리티 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // h2-console 화면을 사용하기 위해 해당 옵션들을 막음(disable)
        http.csrf().disable().headers().frameOptions().disable()

            .and()
                // .authorizeRequests() : URL별 권한 관리 설정 옵션의 시작
                .authorizeRequests()

                // .antMatchers(..antPatterns..) : 권환 관리 대상 지정 옵션
                .antMatchers("/", "/css/**", "/images/**", "/js/**",
                                "/h2-console/**").permitAll()

                // .permitAll() : 전체 열람 권한 부여
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())

                // .anyRequest() : 설정 값 이외 나머지 URL
                // .authenticated() : 인증된 사용자에게만 허용
                .anyRequest().authenticated()

            .and()
                .logout().logoutSuccessUrl("/")

            // oauth2Login() : 로그인 기능 설정 진입점
            // userInfoEndpoint() : 로그인 이후 사용자 정보를 가져올 때 설정 담당
            // userService() : 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스 구현체 등록
            .and()
                .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);

    }

}
