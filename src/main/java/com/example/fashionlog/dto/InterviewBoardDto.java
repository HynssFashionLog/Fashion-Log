package com.example.fashionlog.dto;

import com.example.fashionlog.domain.InterviewBoard;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

	public static InterviewBoard toEntity(InterviewBoardDto interviewBoardDto) {
		return InterviewBoard.builder()
			.id(interviewBoardDto.getId())
			.title(interviewBoardDto.getTitle())
			.content(interviewBoardDto.getContent())
			.status(interviewBoardDto.getStatus())
			.createdAt(interviewBoardDto.getCreatedAt())
			.updatedAt(interviewBoardDto.getUpdatedAt())
			.deletedAt(interviewBoardDto.getDeletedAt())
			.build();
	}
}
