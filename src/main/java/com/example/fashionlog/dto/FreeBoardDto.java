package com.example.fashionlog.dto;

import com.example.fashionlog.domain.FreeBoard;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FreeBoardDto {

	private Long id;
	private String title;
	private String content;
	private Boolean postStatus;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;

	public static FreeBoardDto convertToDto(FreeBoard freeBoard) {
		return FreeBoardDto.builder()
			.id(freeBoard.getId())
			.title(freeBoard.getTitle())
			.content(freeBoard.getContent())
			.postStatus(freeBoard.getPostStatus())
			.createdAt(freeBoard.getCreatedAt())
			.updatedAt(freeBoard.getUpdatedAt())
			.deletedAt(freeBoard.getDeletedAt())
			.build();
	}

	public static FreeBoard convertToEntity(FreeBoardDto freeBoardDto) {
		return FreeBoard.builder()
			.title(freeBoardDto.getTitle())
			.content(freeBoardDto.getContent())
			.postStatus(freeBoardDto.getPostStatus() != null ? freeBoardDto.getPostStatus() : true)
			.createdAt(freeBoardDto.getCreatedAt() != null ? freeBoardDto.getCreatedAt() : LocalDateTime.now())
			.updatedAt(freeBoardDto.getUpdatedAt())
			.deletedAt(freeBoardDto.getDeletedAt())
			.build();
	}
}