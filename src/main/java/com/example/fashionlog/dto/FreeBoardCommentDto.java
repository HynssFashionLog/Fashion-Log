package com.example.fashionlog.dto;

import com.example.fashionlog.domain.CommentUpdatable;
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
public class FreeBoardCommentDto implements CommentUpdatable {

	private Long id; // 댓글 id
	private Long freeBoardId; // 게시물 id
	private Long memberId; // 회원 id
	private String content; // 내용
	private Boolean commentStatus; // 댓글의 삭제 여부(소프트 딜리트)
	private LocalDateTime createdAt; // 댓글 생성일
	private LocalDateTime updatedAt; // 댓글 수정일
	private LocalDateTime deletedAt; // 댓글 삭제일

	// CommentUpdatable getContent 구현
	@Override
	public String getContent() {
		return this.content;
	}

	/**
	 * FreeBoardCommentDto -> FreeBoardComment
	 */
	public static FreeBoardComment convertToEntity(FreeBoard findedFreeBoard,
		FreeBoardCommentDto freeBoardCommentDto) {
		return FreeBoardComment.builder()
			.freeBoard(findedFreeBoard)
			.memberId(freeBoardCommentDto.getMemberId())
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
			.memberId(freeBoardComment.getMemberId())
			.content(freeBoardComment.getContent())
			.commentStatus(freeBoardComment.getCommentStatus())
			.createdAt(freeBoardComment.getCreatedAt())
			.updatedAt(freeBoardComment.getUpdatedAt())
			.deletedAt(freeBoardComment.getDeletedAt())
			.build();
	}
}