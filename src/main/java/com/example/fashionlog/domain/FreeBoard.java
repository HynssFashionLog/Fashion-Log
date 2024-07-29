package com.example.fashionlog.domain;

import com.example.fashionlog.dto.FreeBoardDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 자유 게시판 Entity
 *
 * @author Hynss
 * @version 1.0.0
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class FreeBoard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "freeboard_id")
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	@Column(nullable = false)
	private Boolean postStatus;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column
	private LocalDateTime updatedAt;

	@Column
	private LocalDateTime deletedAt;

	/**
	 * 수정 더티 체킹을 위한 메서드
	 *
	 * @param freeBoardDto 수정 할 값을 받아오기 위한 FreeBoardDto
	 */
	public void updateFreeBoard(FreeBoardDto freeBoardDto) {
		// Not Null 예외 처리
		validateField(freeBoardDto.getTitle(), "Title");
		validateField(freeBoardDto.getContent(), "Content");

		this.title = freeBoardDto.getTitle();
		this.content = freeBoardDto.getContent();
		this.updatedAt = LocalDateTime.now();
	}

	/**
	 * 삭제 더티 체킹을 위한 메서드
	 */
	public void deleteFreeBoard() {
		this.postStatus = Boolean.FALSE;
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