package com.example.fashionlog.domain;

import com.example.fashionlog.dto.MemberDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long memberId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false)
	private String phone;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private Boolean status;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column
	private LocalDateTime updatedAt;

	@Column
	private LocalDateTime deletedAt;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

	/**
	 * 더티 체킹 방식 수정 로직
	 *
	 * @param memberDto 받아올 회원 DTO
	 */
	public void updateRole(MemberDto memberDto, Role role) {
		this.role = role;
		this.updatedAt = LocalDateTime.now();
	}

	/**
	 * 소프트 딜리트를 위한 더티 체킹 방식 삭제 로직
	 */
	public void deleteMember() {
		this.status = Boolean.FALSE;
		this.deletedAt = LocalDateTime.now();
	}
}