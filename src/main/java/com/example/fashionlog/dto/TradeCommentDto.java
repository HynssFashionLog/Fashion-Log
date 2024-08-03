package com.example.fashionlog.dto;

import com.example.fashionlog.domain.CommentUpdatable;
import com.example.fashionlog.domain.Member;
import com.example.fashionlog.domain.Trade;
import com.example.fashionlog.domain.TradeComment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeCommentDto implements CommentUpdatable {

	private Long id;
	private Long tradeId;
	private Long memberId;
	private String content;
	private Boolean commentStatus;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;

	@Override
	public String getContent() {
		return this.content;
	}

	/**
	 * TradeCommentDto -> TradeComment
	 */
	public static TradeComment convertToEntity(Trade findedTrade,
		TradeCommentDto tradeCommentDto, Member member) {
		return TradeComment.builder()
			.member(member)
			.trade(findedTrade)
			.content(tradeCommentDto.getContent())
			.commentStatus(
				tradeCommentDto.getCommentStatus() != null ? tradeCommentDto.getCommentStatus()
					: true)
			.createdAt(tradeCommentDto.getCreatedAt() != null ? tradeCommentDto.getCreatedAt()
				: LocalDateTime.now())
			.updatedAt(tradeCommentDto.getUpdatedAt())
			.deletedAt(tradeCommentDto.getDeletedAt())
			.build();
	}

	/**
	 * TradeComment -> TradeCommentDto
	 */
	public static TradeCommentDto convertToDto(TradeComment tradeComment) {
		return TradeCommentDto.builder()
			.id(tradeComment.getId())
			.memberId(tradeComment.getMember().getMemberId())
			.tradeId(tradeComment.getTrade().getId())
			.content(tradeComment.getContent())
			.commentStatus(tradeComment.getCommentStatus())
			.createdAt(tradeComment.getCreatedAt())
			.updatedAt(tradeComment.getUpdatedAt())
			.deletedAt(tradeComment.getDeletedAt())
			.build();
	}
}
