package com.example.fashionlog.dto;

import com.example.fashionlog.domain.Category;
import com.example.fashionlog.domain.Member;
import com.example.fashionlog.domain.Notice;
import com.example.fashionlog.domain.NoticeUpdatable;
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
	private String authorName;
	private String title;
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
