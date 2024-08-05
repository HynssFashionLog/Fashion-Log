package com.example.fashionlog.dto.board;

import com.example.fashionlog.domain.board.InterviewBoard;
import com.example.fashionlog.domain.Member;
import com.example.fashionlog.domain.baseentity.Updatable;
import jakarta.validation.constraints.Size;
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
	@Size(max = 25, message = "이름 25자를 초과할 수 없습니다.")
	private String authorName;
	@Size(max = 50, message = "이메일은 50자를 초과할 수 없습니다.")
	private String authorEmail;
	@Size(max = 255, message = "제목은 255자를 초과할 수 없습니다.")
	private String title;
	@Size(max = 65535, message = "글자 수는 65535자를 초과할 수 없습니다.")
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
			.authorEmail(interviewBoard.getMember().getEmail())
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
