package com.example.fashionlog.service;

import com.example.fashionlog.domain.Member;
import com.example.fashionlog.repository.MemberRepository;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 사용자 인증을 위한 커스텀 UserDetailsService 구현
 *
 * 이 서비스는 Spring Security의 인증 프로세스에서 사용자 정보를 로드하는 역할을 담당합니다.
 * UserDetailsService 인터페이스를 구현함으로써, Spring Security가 인증 과정에서
 * 이 서비스를 사용하여 사용자 정보를 조회하고 검증할 수 있게 됩니다.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 사용자명(이메일)으로 사용자 정보를 로드하는 메서드
     *
     * 이 메서드는 Spring Security의 인증 과정에서 자동으로 호출됩니다.
     * 사용자가 로그인을 시도할 때, 입력한 사용자명(이메일)을 기반으로
     * 데이터베이스에서 사용자 정보를 조회하고, 이를 Spring Security가
     * 이해할 수 있는 UserDetails 객체로 변환합니다.
     *
     * @param username 사용자명 (이 애플리케이션에서는 이메일을 사용)
     * @return UserDetails 사용자 상세 정보
     * @throws UsernameNotFoundException 사용자를 찾을 수 없을 경우 발생
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username);

        return User.builder()
            .username(member.getEmail())
            .password(member.getPassword())
            .roles(member.getRole().name())
            .build();
    }
}
