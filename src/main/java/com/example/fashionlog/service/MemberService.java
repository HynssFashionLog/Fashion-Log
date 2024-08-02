package com.example.fashionlog.service;

import com.example.fashionlog.domain.Member;
import com.example.fashionlog.domain.Role;
import com.example.fashionlog.dto.MemberDto;
import com.example.fashionlog.repository.MemberRepository;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createMember(MemberDto memberDto) {
        memberDto.setRole(Role.NORMAL);
        memberDto.setCreatedAt(LocalDateTime.now());

        // 비밀번호 암호화
        String encodedPassword = getEncodedPassword(memberDto);
        memberDto.setPassword(encodedPassword);

        Member member = MemberDto.convertToEntity(memberDto);
        memberRepository.save(member);
    }

    // 비밀번호 변환로직
    private String getEncodedPassword(MemberDto memberDto) {
        return passwordEncoder.encode(memberDto.getPassword());
    }
}
