package com.example.fashionlog.dto.comment;

import com.example.fashionlog.domain.baseentity.CommentUpdatable;
import com.example.fashionlog.domain.board.Lookbook;
import com.example.fashionlog.domain.comment.LookbookComment;
import com.example.fashionlog.domain.Member;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LookbookCommentDto implements CommentUpdatable {

	private Long id;
	private Long lookbookId;
	private Long memberId;
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

	// CommentUpdatable getContent 구현
	@Override
	public String getContent() {
		return this.content;
	}

	/**
	 * LookbookCommentDto -> LookbookComment
	 */
	public static LookbookComment convertToEntity(Lookbook findedLookbook,
		LookbookCommentDto lookbookCommentDto, Member member) {
		return LookbookComment.builder()
			.member(member)
			.lookbook(findedLookbook)
			.content(lookbookCommentDto.getContent())
			.commentStatus(lookbookCommentDto.getCommentStatus() != null
				? lookbookCommentDto.getCommentStatus() : true)
			.createdAt(
				lookbookCommentDto.getCreatedAt() != null ? lookbookCommentDto.getCreatedAt()
					: LocalDateTime.now())
			.updatedAt(lookbookCommentDto.getUpdatedAt())
			.deletedAt(lookbookCommentDto.getDeletedAt())
			.build();
	}

	/**
	 * LookbookComment -> LookbookCommentDto
	 */
	public static LookbookCommentDto convertToDto(LookbookComment lookbookComment) {
		return LookbookCommentDto.builder()
			.id(lookbookComment.getId())
			.memberId(lookbookComment.getMember().getMemberId())
			.lookbookId(lookbookComment.getLookbook().getId())
			.authorName(lookbookComment.getMember().getNickname())
			.authorEmail(lookbookComment.getMember().getEmail())
			.content(lookbookComment.getContent())
			.commentStatus(lookbookComment.getCommentStatus())
			.createdAt(lookbookComment.getCreatedAt())
			.updatedAt(lookbookComment.getUpdatedAt())
			.deletedAt(lookbookComment.getDeletedAt())
			.build();
	}
}
