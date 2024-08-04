package com.example.fashionlog.dto;

import com.example.fashionlog.domain.Member;
import com.example.fashionlog.domain.Role;
import jakarta.validation.constraints.Size;
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
    @Size(max = 25, message = "이름은 25자를 초과할 수 없습니다.")
    String name;
    @Size(max = 25, message = "닉네임은 25자를 초과할 수 없습니다.")
    String nickname;
    @Size(max = 15, message = "전화번호는 15자를 초과할 수 없습니다.")
    String phone;
    @Size(max = 50, message = "이메일은 50자를 초과할 수 없습니다.")
    String email;
    @Size(max = 50, message = "비밀번호는 50자를 초과할 수 없습니다.")
    String password;
    Boolean status;
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
            .status(memberDto.getStatus())
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
            .status(member.getStatus())
            .createdAt(member.getCreatedAt())
            .updatedAt(member.getUpdatedAt())
            .deletedAt(member.getDeletedAt())
            .role(member.getRole())
            .build();
    }
}
