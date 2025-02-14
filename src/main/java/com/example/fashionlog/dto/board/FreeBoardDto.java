package com.example.fashionlog.dto.board;

import com.example.fashionlog.domain.board.FreeBoard;
import com.example.fashionlog.domain.Member;
import com.example.fashionlog.domain.baseentity.Updatable;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 자유 게시판 DTO
 *
 * @author Hynss
 * @version 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FreeBoardDto implements Updatable {

	private Long id; // 게시물 id
	private Long memberId; // 회원 id
	@Size(max = 25, message = "이름 25자를 초과할 수 없습니다.")
	private String authorName;
	@Size(max = 50, message = "이메일은 50자를 초과할 수 없습니다.")
	private String authorEmail;
	@Size(max = 255, message = "제목은 255자를 초과할 수 없습니다.")
	private String title; // 게시물 제목
	@Size(max = 65535, message = "글자 수는 65535자를 초과할 수 없습니다.")
	private String content; // 내용
	private Boolean status; // 게시물의 삭제 여부(소프트 딜리트)
	private LocalDateTime createdAt; // 게시물 생성일
	private LocalDateTime updatedAt; // 게시물 수정일
	private LocalDateTime deletedAt; // 게시물 삭제일

	// Updatable getTitle 구현
	@Override
	public String getTitle() {
		return this.title;
	}

	// Updatable getContent 구현
	@Override
	public String getContent() {
		return this.content;
	}

	/**
	 * FreeBoardDto -> FreeBoard
	 */
	public static FreeBoard convertToEntity(FreeBoardDto freeBoardDto, Member member) {
		return FreeBoard.builder()
			.member(member)
			.title(freeBoardDto.getTitle())
			.content(freeBoardDto.getContent())
			.status(freeBoardDto.getStatus() != null ? freeBoardDto.getStatus() : true)
			.createdAt(freeBoardDto.getCreatedAt() != null ? freeBoardDto.getCreatedAt()
				: LocalDateTime.now())
			.updatedAt(freeBoardDto.getUpdatedAt())
			.deletedAt(freeBoardDto.getDeletedAt())
			.build();
	}

	/**
	 * FreeBoard -> FreeBoardDto
	 */
	public static FreeBoardDto convertToDto(FreeBoard freeBoard) {
		return FreeBoardDto.builder()
			.id(freeBoard.getId())
			.memberId(freeBoard.getMember().getMemberId())
			.authorName(freeBoard.getMember().getNickname())
			.authorEmail(freeBoard.getMember().getEmail())
			.title(freeBoard.getTitle())
			.content(freeBoard.getContent())
			.status(freeBoard.getStatus())
			.createdAt(freeBoard.getCreatedAt())
			.updatedAt(freeBoard.getUpdatedAt())
			.deletedAt(freeBoard.getDeletedAt())
			.build();
	}
}