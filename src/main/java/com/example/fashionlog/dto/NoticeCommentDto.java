package com.example.fashionlog.dto;

import com.example.fashionlog.domain.CommentUpdatable;
import com.example.fashionlog.domain.Member;
import com.example.fashionlog.domain.Notice;
import com.example.fashionlog.domain.NoticeComment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeCommentDto implements CommentUpdatable {

	private Long id;
	private Long memberId;
	private Long noticeId;
	private String content;
	private Boolean commentStatus;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;

	@Override
	public String getContent() {
		return this.content;
	}

	public static NoticeCommentDto convertToDto(NoticeComment noticeComment) {
		return NoticeCommentDto.builder()
			.id(noticeComment.getId())
			.memberId(noticeComment.getMember().getMemberId())
			.noticeId(noticeComment.getNotice().getId())
			.content(noticeComment.getContent())
			.commentStatus(noticeComment.getCommentStatus())
			.createdAt(noticeComment.getCreatedAt())
			.updatedAt(noticeComment.getUpdatedAt())
			.deletedAt(noticeComment.getDeletedAt())
			.build();
	}

	public static NoticeComment convertToEntity(NoticeCommentDto noticeCommentDto, Notice notice, Member member) {
		return NoticeComment.builder()
			.id(noticeCommentDto.getId())
			.member(member)
			.notice(notice)
			.content(noticeCommentDto.getContent())
			.commentStatus(noticeCommentDto.getCommentStatus())
			.createdAt(LocalDateTime.now())
			.updatedAt(noticeCommentDto.getUpdatedAt())
			.deletedAt(noticeCommentDto.getDeletedAt())
			.build();
	}

}
