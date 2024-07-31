package com.example.fashionlog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
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
 * 댓글 Base Entity
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
public abstract class CommentBaseEntity {

	// 내용
	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	// 댓글이 삭제 되었는지 여부 (소프트 딜리트)
	@Column(nullable = false)
	private Boolean commentStatus;

	// 댓글 생성일
	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	// 댓글 수정일
	@LastModifiedDate
	@Column
	private LocalDateTime updatedAt;

	// 댓글 삭제일
	@Column
	private LocalDateTime deletedAt;

	/**
	 * 더티 체킹 방식 수정 로직
	 *
	 * @param commentDto 내용을 받아 오기 위한 DTO
	 * @param <T> T에는 댓글 클래스가 들어감
	 */
	public <T extends CommentUpdatable> void updateComment(T commentDto) {
		// Not Null 예외 처리
		validateField(commentDto.getContent(), "Content");

		this.content = commentDto.getContent();
		this.updatedAt = LocalDateTime.now();
	}

	/**
	 * 소프트 딜리트를 위한 더티 체킹 방식 삭제 로직
	 */
	public void deleteComment() {
		this.commentStatus = Boolean.FALSE;
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
}