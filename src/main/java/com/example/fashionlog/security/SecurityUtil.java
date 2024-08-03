package com.example.fashionlog.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 보안 관련 유틸리티 클래스입니다.
 * 현재 인증된 사용자의 정보를 가져오는 메서드를 제공합니다.
 */
@Component
public class SecurityUtil {

    /**
     * 현재 인증된 사용자의 이메일(사용자명)을 반환합니다.
     *
     * 이 메서드는 Spring Security의 SecurityContextHolder를 사용하여
     * 현재 인증된 사용자의 정보에 접근합니다.
     *
     * @return 현재 인증된 사용자의 이메일(사용자명).
     *         인증된 사용자가 없거나 인증되지 않은 경우 null을 반환합니다.
     */
    public static String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return authentication.getName();
    }
}
