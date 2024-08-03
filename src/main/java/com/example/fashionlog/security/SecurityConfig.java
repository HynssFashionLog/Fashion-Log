package com.example.fashionlog.security;

import com.example.fashionlog.domain.Role;
import com.example.fashionlog.service.CustomUserDetailsService;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * Spring Security 설정 클래스. 애플리케이션의 보안 규칙과 인증/인가 설정을 정의합니다.
 *
 * @author Hynns
 * @version 1.0.0
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final DataSource dataSource;

    /**
     * 보안 필터 체인을 구성합니다.
     *
     * @param http HttpSecurity 객체
     * @return 구성된 SecurityFilterChain
     * @throws Exception 보안 구성 중 발생할 수 있는 예외
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer -> configurer
                // 정적 리소르에 대한 접근 허용
                .requestMatchers("/css/**", "/js/**", "/image/**").permitAll()
                // 메인 페이지는 인증된 사용자만 접근 가능
                .requestMatchers("/fashionlog").authenticated()
                // 회원가입 페이지는 모든 사용자에게 접근 허용
                .requestMatchers("/fashionlog/sign-up").permitAll()
                // 관리자 페이지는 ADMIN 역할을 가진 사용자만 접근 가능
                .requestMatchers("/fashionlog/management").hasRole(Role.ADMIN.name())
                // 공지사항 페이지는 인증된 사용자만 접근 가능
                .requestMatchers("/fashionlog/notice").authenticated()
                // 공지사항 새 글 등록은 ADMIN 역할을 가진 사용자만 접근 가능
                .requestMatchers("/fashionlog/notice/new").hasRole(Role.ADMIN.name())
                // 공지사항 글은 인증된 사용자만 접근 가능
                .requestMatchers("/fashionlog/notice/**").authenticated()
                // /fashionlog/** 경로는 NORMAL 또는 ADMIN 역할을 가진 사용자만 접근 가능
                .requestMatchers("/fashionlog/**").hasAnyRole(Role.NORMAL.name(), Role.ADMIN.name())
                // 그 외 모든 요청은 인증된 사용자만 접근 가능
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                // 사용자 정의 로그인 페이지 설정
                .loginPage("/fashionlog/login")
                // 로그인 처리 URL 설정
                .loginProcessingUrl("/authenticateTheUser")
                // 로그인 성공 후 리다이렉트할 기본 URL 설정
                .defaultSuccessUrl("/fashionlog", true)
                // 로그인 페이지는 모든 사용자에게 접근 허용
                .permitAll()
            )
            .rememberMe(remember -> remember
                // Remember-Me 쿠키를 암호화하는 데 사용되는 고유한 키
                .key("uniqueAndSecret")
                // Remember-Me 기능을 활성화하는 체크박스의 파라미터 이름
                .rememberMeParameter("remember-me")
                // Remember-Me 토큰의 유효 기간을 86400초(24시간)로 설정
                .tokenValiditySeconds(86400)
                // 영구적인 토큰 저장소 설정 (DB에 토큰 정보 저장)
                .tokenRepository(persistentTokenRepository())
            )
            .logout(logout -> logout
                // 로그아웃 URL을 변경
                .logoutUrl("/logout")
                // 로그아웃 성공 시 리다이렉트할 URL
                .logoutSuccessUrl("/login?logout")
                // 쿠키 삭제
                .deleteCookies("JSESSIONID", "remember-me")
                // 세션 무효화
                .invalidateHttpSession(true)
                // 로그아웃은 모든 사용자에게 접근 허용
                .permitAll()
            )
            .userDetailsService(customUserDetailsService);

        return http.build();
    }

    /**
     * Remember Me 기능을 위한 PersistentTokenRepository 빈을 생성합니다.
     *
     * CREATE TABLE persistent_logins ( username VARCHAR(64) NOT NULL, series VARCHAR(64) PRIMARY
     * KEY, token VARCHAR(64) NOT NULL, last_used TIMESTAMP NOT NULL ); 로 persistent_logins DB에 추가한
     * 후 사용 가능
     *
     * persistent_logins 엔티티 구현으로 자동 생성
     *
     * @return tokenRepository
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }
}