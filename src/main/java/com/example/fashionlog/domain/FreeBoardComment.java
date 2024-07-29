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

/**
 * 자유 게시판 댓글 Entity
 *
 * @author Hynss
 * @version 1.0.0
 */
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

	/**
	 * 수정 더티 체킹을 위한 메서드
	 *
	 * @param freeBoardCommentDto 수정 할 값을 받아오기 위한 FreeBoardCommentDto
	 */
	public void updateFreeBoardComment(FreeBoardCommentDto freeBoardCommentDto) {
		validateField(freeBoardCommentDto.getContent(), "Content");

		this.content = freeBoardCommentDto.getContent();
		this.updatedAt = LocalDateTime.now();
	}

	/**
	 * 삭제 더티 체킹을 위한 메서드
	 */
	public void deleteFreeBoardComment() {
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