package com.example.fashionlog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    /**
     * PasswordEncoder 빈 생성 메서드
     *
     * Spring이 애플리케이션 시작 시 이 설정 클래스를 읽습니다.
     * 2. @Bean 어노테이션이 붙은 메서드를 실행하여 반환된 객체를 빈으로 등록합니다.
     * 3. 다른 컴포넌트(예: MemberService)에서 PasswordEncoder 타입의 의존성을 요청하면,
     *    Spring이 여기서 생성된 BCryptPasswordEncoder 인스턴스를 주입합니다.
     * 4. 예를 들어, @Autowired나 생성자 주입을 통해 PasswordEncoder를 요청하는 곳에
     *    이 빈이 자동으로 주입됩니다.
     * @return BCryptPasswordEncoder 인스턴스
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
