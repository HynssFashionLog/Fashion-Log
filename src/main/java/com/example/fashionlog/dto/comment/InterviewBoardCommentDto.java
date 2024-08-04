package com.example.fashionlog.dto.comment;

import com.example.fashionlog.domain.baseentity.CommentUpdatable;
import com.example.fashionlog.domain.board.InterviewBoard;
import com.example.fashionlog.domain.comment.InterviewBoardComment;
import com.example.fashionlog.domain.Member;
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
	private Long memberId;
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
			.memberId(interviewBoardComment.getMember().getMemberId())
			.boardId(interviewBoardComment.getInterviewBoard().getId())
			.content(interviewBoardComment.getContent())
			.commentStatus(interviewBoardComment.getCommentStatus())
			.createdAt(interviewBoardComment.getCreatedAt())
			.updatedAt(interviewBoardComment.getUpdatedAt())
			.deletedAt(interviewBoardComment.getDeletedAt())
			.build();
	}

	public InterviewBoardComment toEntity(InterviewBoardCommentDto interviewBoardCommentDto,
		InterviewBoard interviewBoard, Member member) {
		return InterviewBoardComment.builder()
			.id(interviewBoardCommentDto.getId())
			.member(member)
			.interviewBoard(interviewBoard)
			.content(interviewBoardCommentDto.getContent())
			.commentStatus(interviewBoardCommentDto.getCommentStatus())
			.createdAt(interviewBoardCommentDto.getCreatedAt())
			.updatedAt(interviewBoardCommentDto.getUpdatedAt())
			.deletedAt(interviewBoardCommentDto.getDeletedAt())
			.build();
	}
}
