package com.example.fashionlog.dto;

import com.example.fashionlog.domain.InterviewBoard;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewBoardDto {

	private Long id;
	private String title;
	private String content;
	private Boolean status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;

	public static InterviewBoardDto fromEntity(InterviewBoard interviewBoard) {
		return InterviewBoardDto.builder()
			.id(interviewBoard.getId())
			.title(interviewBoard.getTitle())
			.content(interviewBoard.getContent())
			.status(interviewBoard.getStatus())
			.createdAt(interviewBoard.getCreatedAt())
			.updatedAt(interviewBoard.getUpdatedAt())
			.deletedAt(interviewBoard.getDeletedAt())
			.build();

	}

	public InterviewBoard toEntity() {
		return new InterviewBoard(
			this.id,
			this.title,
			this.content,
			this.status,
			this.createdAt,
			this.updatedAt,
			this.updatedAt
		);
	}
}
