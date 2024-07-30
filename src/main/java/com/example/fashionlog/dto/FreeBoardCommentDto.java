package com.example.fashionlog.dto;

import com.example.fashionlog.domain.FreeBoard;
import com.example.fashionlog.domain.FreeBoardComment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 자유 게시판 댓글 DTO
 *
 * @author Hynss
 * @version 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FreeBoardCommentDto {

	private Long id;
	private Long freeBoardId;
	private String content;
	private Boolean commentStatus;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;

	/**
	 * FreeBoardCommentDto -> FreeBoardComment
	 */
	public static FreeBoardComment convertToEntity(FreeBoard findedFreeBoard,
		FreeBoardCommentDto freeBoardCommentDto) {
		return FreeBoardComment.builder()
			.freeBoard(findedFreeBoard)
			.content(freeBoardCommentDto.getContent())
			.commentStatus(freeBoardCommentDto.getCommentStatus() != null
				? freeBoardCommentDto.getCommentStatus() : true)
			.createdAt(
				freeBoardCommentDto.getCreatedAt() != null ? freeBoardCommentDto.getCreatedAt()
					: LocalDateTime.now())
			.updatedAt(freeBoardCommentDto.getUpdatedAt())
			.deletedAt(freeBoardCommentDto.getDeletedAt())
			.build();
	}

	/**
	 * FreeBoardComment -> FreeBoardCommentDto
	 */
	public static FreeBoardCommentDto convertToDto(FreeBoardComment freeBoardComment) {
		return FreeBoardCommentDto.builder()
			.id(freeBoardComment.getId())
			.freeBoardId(freeBoardComment.getFreeBoard().getId())
			.content(freeBoardComment.getContent())
			.commentStatus(freeBoardComment.getCommentStatus())
			.createdAt(freeBoardComment.getCreatedAt())
			.updatedAt(freeBoardComment.getUpdatedAt())
			.deletedAt(freeBoardComment.getDeletedAt())
			.build();
	}
}