package com.example.fashionlog.dto;

import com.example.fashionlog.domain.Member;
import com.example.fashionlog.domain.Role;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link Member}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MemberDto {

    Long memberId;
    String name;
    String nickname;
    String phone;
    String email;
    String password;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    LocalDateTime deletedAt;
    Role role;

    /**
     * MemberDto -> Member
     */
    public static Member convertToEntity(MemberDto memberDto) {
        return Member.builder()
            .memberId(memberDto.getMemberId())
            .name(memberDto.getName())
            .nickname(memberDto.getNickname())
            .phone(memberDto.getPhone())
            .email(memberDto.getEmail())
            .password(memberDto.getPassword())
            .createdAt(LocalDateTime.now())
            .updatedAt(memberDto.getUpdatedAt())
            .deletedAt(memberDto.getDeletedAt())
            .role(memberDto.getRole())
            .build();
    }

    /**
     * Member -> MemberDto
     */
    public static MemberDto convertToDto(Member member) {
        return MemberDto.builder()
            .memberId(member.getMemberId())
            .name(member.getName())
            .nickname(member.getNickname())
            .phone(member.getPhone())
            .email(member.getEmail())
            .password(member.getPassword())
            .createdAt(member.getCreatedAt())
            .updatedAt(member.getUpdatedAt())
            .deletedAt(member.getDeletedAt())
            .role(member.getRole())
            .build();
    }
}
