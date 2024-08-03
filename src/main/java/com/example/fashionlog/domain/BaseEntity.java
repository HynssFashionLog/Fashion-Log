package com.example.fashionlog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 게시글 Base Entity
 *
 * @author Hynss
 * @version 1.0.0
 */
@Getter
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

	// 회원 id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	// 제목
	@Column(nullable = false)
	private String title;

	// 내용
	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	// 게시글이 삭제 되었는지 여부 (소프트 딜리트)
	@Column(nullable = false)
	private Boolean status;

	// 게시글 생성일
	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	// 게시글 수정일
	@LastModifiedDate
	@Column
	private LocalDateTime updatedAt;

	// 게시글 삭제일
	@Column
	private LocalDateTime deletedAt;

	/**
	 * 더티 체킹 방식 수정 로직
	 *
	 * @param dto 제목과 내용을 받아 오기 위한 DTO
	 * @param <T> T에는 게시판 클래스가 들어감
	 */
	public <T extends Updatable> void update(T dto) {
		// Not Null 예외 처리
		validateField(dto.getTitle(), "Title");
		validateField(dto.getContent(), "Content");

		this.title = dto.getTitle();
		this.content = dto.getContent();
	}

	/**
	 * 소프트 딜리트를 위한 더티 체킹 방식 삭제 로직
	 */
	public void delete() {
		this.status = Boolean.FALSE;
		this.deletedAt = LocalDateTime.now();
	}

	/**
	 * Not Null 예외 처리 로직
	 *
	 * @param field 속성
	 * @param fieldName 속성의 이름
	 */
	public static void validateField(String field, String fieldName) {
		if (field == null || field.isEmpty()) {
			throw new IllegalArgumentException(fieldName + " cannot be null or empty");
		}
	}

	/**
	 * 작성자 확인 메소드
	 *
	 * @param memberEmail 확인할 회원의 이메일
	 * @return 작성자 여부
	 */
	public boolean isAuthor(String memberEmail) {
		return this.member.getEmail().equals(memberEmail);
	}
}