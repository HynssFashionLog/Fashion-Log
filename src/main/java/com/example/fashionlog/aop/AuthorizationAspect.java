package com.example.fashionlog.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * API 권한 검증 Aspect
 *
 * @author Hynss
 * @version 1.0.0
 */
@Aspect
@Component
public class AuthorizationAspect {

	// AuthCheck 어노테이션이 붙은 메서드 실행 전에 이 advice를 실행
	@Before("@annotation(authCheck)")
	public void checkPermission(AuthCheck authCheck) {
		// 현재 인증된 사용자의 정보를 가져옴
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		boolean hasPermission = false;
		// AuthCheck 어노테이션에 지정된 모든 역할에 대해 검사
		for (String role : authCheck.value()) {
			// 사용자가 해당 역할을 가지고 있는지 확인
			if (userDetails.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role))) {
				hasPermission = true;
				break; // 하나라도 일치하는 역할이 있으면 반복 종료
			}
		}

		// 권한이 없으면 SecurityException 발생
		if (!hasPermission) {
			throw new SecurityException("권한이 없습니다.");
		}
	}
}