package com.example.fashionlog.dto.board;

import com.example.fashionlog.domain.Category;
import com.example.fashionlog.domain.Member;
import com.example.fashionlog.domain.board.Notice;
import com.example.fashionlog.domain.baseentity.NoticeUpdatable;
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
public class NoticeDto implements NoticeUpdatable {

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
	private Category category;

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public String getContent() {
		return this.content;
	}

	@Override
	public Category getCategory() {
		return this.category;
	}

	public static NoticeDto convertToDto(Notice notice) {
		return NoticeDto.builder()
			.id(notice.getId())
			.memberId(notice.getMember().getMemberId())
			.authorName(notice.getMember().getNickname())
			.authorEmail(notice.getMember().getEmail())
			.title(notice.getTitle())
			.content(notice.getContent())
			.status(notice.getStatus())
			.createdAt(notice.getCreatedAt())
			.updatedAt(notice.getUpdatedAt())
			.deletedAt(notice.getDeletedAt())
			.category(notice.getCategory())
			.build();
	}

	public static Notice convertToEntity(NoticeDto noticeDto, Member member) {
		return Notice.builder()
			.id(noticeDto.getId())
			.member(member)
			.title(noticeDto.getTitle())
			.content(noticeDto.getContent())
			.status(noticeDto.getStatus())
			.createdAt(LocalDateTime.now())
			.updatedAt(noticeDto.getUpdatedAt())
			.deletedAt(noticeDto.getDeletedAt())
			.category(noticeDto.getCategory())
			.build();
	}
}
