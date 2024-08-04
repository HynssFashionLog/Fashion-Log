package com.example.fashionlog.dto.board;

import com.example.fashionlog.domain.board.Lookbook;
import com.example.fashionlog.domain.Member;
import com.example.fashionlog.domain.baseentity.Updatable;
import jakarta.validation.constraints.Size;
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
	@Size(max = 25, message = "이름은 25자를 초과할 수 없습니다.")
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
			.authorEmail(lookbook.getMember().getEmail())
			.title(lookbook.getTitle())
			.content(lookbook.getContent())
			.status(lookbook.getStatus())
			.createdAt(lookbook.getCreatedAt())
			.updatedAt(lookbook.getUpdatedAt())
			.deletedAt(lookbook.getDeletedAt())
			.build();
	}
}