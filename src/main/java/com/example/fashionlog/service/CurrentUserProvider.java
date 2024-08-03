package com.example.fashionlog.service;

import com.example.fashionlog.domain.Member;
import com.example.fashionlog.repository.MemberRepository;
import com.example.fashionlog.security.SecurityUtil;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 현재 인증된 사용자의 정보를 제공하는 컴포넌트입니다.
 * 이 클래스는 SecurityUtil을 사용하여 현재 인증된 사용자의 이메일을 가져오고,
 * 이를 바탕으로 데이터베이스에서 해당 사용자의 전체 정보를 조회합니다.
 */
@Component
public class CurrentUserProvider {

    private final MemberRepository memberRepository;

    public CurrentUserProvider(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 현재 인증된 사용자의 Member 엔티티를 조회합니다.
     * 이 메서드는 읽기 전용 트랜잭션 내에서 실행됩니다.
     *
     * @return 현재 인증된 사용자의 Member 엔티티.
     *         인증된 사용자가 없거나 데이터베이스에서 찾을 수 없는 경우 null을 반환할 수 있습니다.
     */
    @Transactional(readOnly = true)
    public Member getCurrentUser() {
        String currentUserEmail = SecurityUtil.getCurrentUserEmail();
        return memberRepository.findByEmailAndStatusIsTrue(currentUserEmail);
    }

}
