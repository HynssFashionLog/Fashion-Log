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

	public void updateFreeBoard(FreeBoardDto freeBoardDto) {
		validateField(freeBoardDto.getTitle(), "Title");
		validateField(freeBoardDto.getContent(), "Content");

		this.title = freeBoardDto.getTitle();
		this.content = freeBoardDto.getContent();
		this.updatedAt = LocalDateTime.now();
	}

	public void deleteFreeBoard() {
		this.postStatus = Boolean.FALSE;
		this.deletedAt = LocalDateTime.now();
	}

	public static void validateField(String field, String fieldName) {
		if (field == null || field.isEmpty()) {
			throw new IllegalArgumentException(fieldName + " cannot be null or empty");
		}
	}
}