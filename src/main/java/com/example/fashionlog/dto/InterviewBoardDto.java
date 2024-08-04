package com.example.fashionlog.dto;

import com.example.fashionlog.domain.InterviewBoard;
import com.example.fashionlog.domain.Member;
import com.example.fashionlog.domain.Updatable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewBoardDto implements Updatable {

	private Long id;
	private Long memberId;
	private String authorName;
	private String title;
	private String content;
	private Boolean status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public String getContent() {
		return this.content;
	}

	public static InterviewBoardDto fromEntity(InterviewBoard interviewBoard) {
		return InterviewBoardDto.builder()
			.id(interviewBoard.getId())
			.memberId(interviewBoard.getMember().getMemberId())
			.authorName(interviewBoard.getMember().getNickname())
			.title(interviewBoard.getTitle())
			.content(interviewBoard.getContent())
			.status(interviewBoard.getStatus())
			.createdAt(interviewBoard.getCreatedAt())
			.updatedAt(interviewBoard.getUpdatedAt())
			.deletedAt(interviewBoard.getDeletedAt())
			.build();

	}

	public static InterviewBoard toEntity(InterviewBoardDto interviewBoardDto, Member member) {
		return InterviewBoard.builder()
			.id(interviewBoardDto.getId())
			.member(member)
			.title(interviewBoardDto.getTitle())
			.content(interviewBoardDto.getContent())
			.status(interviewBoardDto.getStatus())
			.createdAt(interviewBoardDto.getCreatedAt())
			.updatedAt(interviewBoardDto.getUpdatedAt())
			.deletedAt(interviewBoardDto.getDeletedAt())
			.build();
	}
}
