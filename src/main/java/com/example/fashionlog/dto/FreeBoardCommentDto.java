package com.example.fashionlog.dto;

import com.example.fashionlog.domain.FreeBoardComment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FreeBoardCommentDto {

	private Long id;
	private Long freeBoardId;
	private String content;
	private Boolean commentStatus;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;

	public static FreeBoardCommentDto convertToDto(FreeBoardComment freeBoardComment) {
		return FreeBoardCommentDto.builder()
			.id(freeBoardComment.getId())
			.freeBoardId(freeBoardComment.getFreeBoard().getId())
			.content(freeBoardComment.getContent())
			.commentStatus(freeBoardComment.getComment_status())
			.createdAt(freeBoardComment.getCreatedAt())
			.updatedAt(freeBoardComment.getUpdatedAt())
			.deletedAt(freeBoardComment.getDeletedAt())
			.build();
	}
}