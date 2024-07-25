package com.example.fashionlog.dto;

import com.example.fashionlog.domain.FreeBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class FreeBoardDto {

	private Long id;
	private String title;
	private String content;
	private Boolean postStatus;

	public static FreeBoardDto convertToDto(FreeBoard freeBoard) {
		return FreeBoardDto.builder()
			.id(freeBoard.getId())
			.title(freeBoard.getTitle())
			.content(freeBoard.getContent())
			.postStatus(freeBoard.getPostStatus())
			.build();
	}
}