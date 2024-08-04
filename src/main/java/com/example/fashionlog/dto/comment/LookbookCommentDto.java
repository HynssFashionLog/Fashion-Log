package com.example.fashionlog.dto.comment;

import com.example.fashionlog.domain.baseentity.CommentUpdatable;
import com.example.fashionlog.domain.board.Lookbook;
import com.example.fashionlog.domain.comment.LookbookComment;
import com.example.fashionlog.domain.Member;
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
			.content(lookbookComment.getContent())
			.commentStatus(lookbookComment.getCommentStatus())
			.createdAt(lookbookComment.getCreatedAt())
			.updatedAt(lookbookComment.getUpdatedAt())
			.deletedAt(lookbookComment.getDeletedAt())
			.build();
	}
}
