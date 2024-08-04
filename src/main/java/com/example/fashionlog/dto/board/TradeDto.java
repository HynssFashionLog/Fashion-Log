package com.example.fashionlog.dto.board;

import com.example.fashionlog.domain.Member;
import com.example.fashionlog.domain.board.Trade;
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
public class TradeDto implements Updatable {

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

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public String getContent() {
		return this.content;
	}

	public static Trade convertToEntity(TradeDto tradeDto, Member member) {
		return Trade.builder()
			.member(member)
			.title(tradeDto.getTitle())
			.content(tradeDto.getContent())
			.status(tradeDto.getStatus() != null ? tradeDto.getStatus() : true)
			.createdAt(
				tradeDto.getCreatedAt() != null ? tradeDto.getCreatedAt() : LocalDateTime.now())
			.updatedAt(tradeDto.getUpdatedAt())
			.deletedAt(tradeDto.getDeletedAt())
			.build();
	}

	public static TradeDto convertToDto(Trade trade) {
		return TradeDto.builder()
			.id(trade.getId())
			.memberId(trade.getMember().getMemberId())
			.authorName(trade.getMember().getNickname())
			.authorEmail(trade.getMember().getEmail())
			.title(trade.getTitle())
			.content(trade.getContent())
			.status(trade.getStatus())
			.createdAt(trade.getCreatedAt())
			.updatedAt(trade.getUpdatedAt())
			.deletedAt(trade.getDeletedAt())
			.build();
	}
}
