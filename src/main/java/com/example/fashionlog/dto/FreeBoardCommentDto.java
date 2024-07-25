package com.example.fashionlog.dto;

import com.example.fashionlog.domain.FreeBoardComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class FreeBoardCommentDto {

	private Long id;
	private Long freeBoardId;
	private String content;
	private Boolean comment_status;

	public static FreeBoardCommentDto convertToDto(FreeBoardComment freeBoardComment) {
		return FreeBoardCommentDto.builder()
			.id(freeBoardComment.getId())
			.freeBoardId(freeBoardComment.getFreeBoard().getId())
			.content(freeBoardComment.getContent())
			.comment_status(freeBoardComment.getComment_status())
			.build();
	}
}