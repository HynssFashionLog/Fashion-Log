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

@Getter
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class CommentBaseEntity {

	@Column(nullable = false)
	private String content;

	@Column(nullable = false)
	private Boolean commentStatus;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column
	private LocalDateTime updatedAt;

	@Column
	private LocalDateTime deletedAt;

	public <T extends CommentUpdatable> void updateComment(T commentDto) {
		// Not Null 예외 처리
		validateField(commentDto.getContent(), "Content");

		this.content = commentDto.getContent();
		this.updatedAt = LocalDateTime.now();
	}

	public void deleteComment() {
		this.commentStatus = Boolean.FALSE;
		this.deletedAt = LocalDateTime.now();
	}

	/**
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