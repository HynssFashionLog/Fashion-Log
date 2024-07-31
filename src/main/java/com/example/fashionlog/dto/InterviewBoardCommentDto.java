package com.example.fashionlog.dto;

import com.example.fashionlog.domain.CommentUpdatable;
import com.example.fashionlog.domain.InterviewBoard;
import com.example.fashionlog.domain.InterviewBoardComment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewBoardCommentDto implements CommentUpdatable {

	private Long id;
	private Long boardId;
	private String content;
	private Boolean commentStatus;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;

	@Override
	public String getContent() {
		return this.content;
	}

	public static InterviewBoardCommentDto fromEntity(InterviewBoardComment interviewBoardComment) {
		return InterviewBoardCommentDto.builder()
			.id(interviewBoardComment.getId())
			.boardId(interviewBoardComment.getInterviewBoard().getId())
			.content(interviewBoardComment.getContent())
			.commentStatus(interviewBoardComment.getCommentStatus())
			.createdAt(interviewBoardComment.getCreatedAt())
			.updatedAt(interviewBoardComment.getUpdatedAt())
			.deletedAt(interviewBoardComment.getDeletedAt())
			.build();
	}

	public InterviewBoardComment toEntity(InterviewBoardCommentDto interviewBoardCommentDto,
		InterviewBoard interviewBoard) {
		return InterviewBoardComment.builder()
			.id(interviewBoardCommentDto.getId())
			.interviewBoard(interviewBoard)
			.content(interviewBoardCommentDto.getContent())
			.commentStatus(interviewBoardCommentDto.getCommentStatus())
			.createdAt(interviewBoardCommentDto.getCreatedAt())
			.updatedAt(interviewBoardCommentDto.getUpdatedAt())
			.deletedAt(interviewBoardCommentDto.getDeletedAt())
			.build();
	}
}
