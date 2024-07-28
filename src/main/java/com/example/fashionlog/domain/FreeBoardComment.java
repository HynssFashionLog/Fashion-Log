package com.example.fashionlog.domain;

import com.example.fashionlog.dto.FreeBoardCommentDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class FreeBoardComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "freeboard_comment_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "freeboard_id", nullable = false)
	private FreeBoard freeBoard;

	@Column(nullable = false)
	private String content;

	@Column(nullable = false)
	private Boolean commentStatus;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column
	private LocalDateTime updatedAt;

	@Column
	private LocalDateTime deletedAt;

	public void updateFreeBoardComment(FreeBoardCommentDto freeBoardCommentDto) {
		validateField(freeBoardCommentDto.getContent(), "Content");

		this.content = freeBoardCommentDto.getContent();
		this.updatedAt = LocalDateTime.now();
	}

	public void deleteFreeBoardComment() {
		this.commentStatus = Boolean.FALSE;
		this.deletedAt = LocalDateTime.now();
	}

	public static void validateField(String field, String fieldName) {
		if (field == null || field.isEmpty()) {
			throw new IllegalArgumentException(fieldName + " cannot be null or empty");
		}
	}
}