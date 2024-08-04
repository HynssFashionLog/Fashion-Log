package com.example.fashionlog.dto;

import com.example.fashionlog.domain.FreeBoard;
import com.example.fashionlog.domain.Lookbook;
import com.example.fashionlog.domain.Member;
import com.example.fashionlog.domain.Updatable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LookbookDto implements Updatable {

	private Long id;
	private Long memberId;
	private String authorName;
	private String title;
	private String content;
	private Boolean status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;

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


	public static Lookbook convertToEntity(LookbookDto lookbookDto, Member member) {
		return Lookbook.builder()
			.member(member)
			.title(lookbookDto.getTitle())
			.content(lookbookDto.getContent())
			.status(lookbookDto.getStatus() != null ? lookbookDto.getStatus() : true)
			.createdAt(lookbookDto.getCreatedAt() != null ? lookbookDto.getCreatedAt()
				: LocalDateTime.now())
			.updatedAt(lookbookDto.getUpdatedAt())
			.deletedAt(lookbookDto.getDeletedAt())
			.build();
	}

	/**
	 * Lookbook -> LookbookDto
	 */
	public static LookbookDto convertToDto(Lookbook lookbook) {
		return LookbookDto.builder()
			.id(lookbook.getId())
			.memberId(lookbook.getMember().getMemberId())
			.authorName(lookbook.getMember().getNickname())
			.title(lookbook.getTitle())
			.content(lookbook.getContent())
			.status(lookbook.getStatus())
			.createdAt(lookbook.getCreatedAt())
			.updatedAt(lookbook.getUpdatedAt())
			.deletedAt(lookbook.getDeletedAt())
			.build();
	}
}