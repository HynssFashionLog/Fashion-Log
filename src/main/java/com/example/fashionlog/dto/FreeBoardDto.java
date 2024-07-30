package com.example.fashionlog.dto;

import com.example.fashionlog.domain.FreeBoard;
import com.example.fashionlog.domain.Updatable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 자유 게시판 DTO
 *
 * @author Hynss
 * @version 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FreeBoardDto implements Updatable {

	private Long id; // 게시물 id
	private String title; // 게시물 제목
	private String content; // 내용
	private Boolean status; // 게시물의 삭제 여부(소프트 딜리트)
	private LocalDateTime createdAt; // 게시물 생성일
	private LocalDateTime updatedAt; // 게시물 수정일
	private LocalDateTime deletedAt; // 게시물 삭제일

	// Updatable getTitle 구현
	@Override
	public String getTitle() {
		return this.title;
	}

	// Updatable getContent 구현
	@Override
	public String getContent() {
		return this.content;
	}

	/**
	 * FreeBoardDto -> FreeBoard
	 */
	public static FreeBoard convertToEntity(FreeBoardDto freeBoardDto) {
		return FreeBoard.builder()
			.title(freeBoardDto.getTitle())
			.content(freeBoardDto.getContent())
			.status(freeBoardDto.getStatus() != null ? freeBoardDto.getStatus() : true)
			.createdAt(freeBoardDto.getCreatedAt() != null ? freeBoardDto.getCreatedAt()
				: LocalDateTime.now())
			.updatedAt(freeBoardDto.getUpdatedAt())
			.deletedAt(freeBoardDto.getDeletedAt())
			.build();
	}

	/**
	 * FreeBoard -> FreeBoardDto
	 */
	public static FreeBoardDto convertToDto(FreeBoard freeBoard) {
		return FreeBoardDto.builder()
			.id(freeBoard.getId())
			.title(freeBoard.getTitle())
			.content(freeBoard.getContent())
			.status(freeBoard.getStatus())
			.createdAt(freeBoard.getCreatedAt())
			.updatedAt(freeBoard.getUpdatedAt())
			.deletedAt(freeBoard.getDeletedAt())
			.build();
	}
}