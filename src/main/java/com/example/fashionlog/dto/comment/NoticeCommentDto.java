package com.example.fashionlog.dto.comment;

import com.example.fashionlog.domain.baseentity.CommentUpdatable;
import com.example.fashionlog.domain.Member;
import com.example.fashionlog.domain.board.Notice;
import com.example.fashionlog.domain.comment.NoticeComment;
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
public class NoticeCommentDto implements CommentUpdatable {

	private Long id;
	private Long memberId;
	private Long noticeId;
	@Size(max = 25, message = "이름은 25자를 초과할 수 없습니다.")
	private String authorName;
	@Size(max = 50, message = "이메일은 50자를 초과할 수 없습니다.")
	private String authorEmail;
	@Size(max = 65535, message = "글자 수는 65535자를 초과할 수 없습니다.")
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
			.authorName(noticeComment.getMember().getNickname())
			.authorEmail(noticeComment.getMember().getEmail())
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
