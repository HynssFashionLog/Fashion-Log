package com.example.fashionlog.dto;

import com.example.fashionlog.domain.Trade;
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
public class TradeDto implements Updatable {

	private Long id;
	private String title;
	private String content;
	private Boolean status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;

	@Override
	public String getTitle(){
		return this.title;
	}

	@Override
	public String getContent(){
		return this.content;
	}

	public static Trade convertToEntity(TradeDto tradeDto){
		return Trade.builder()
			.title(tradeDto.getTitle())
			.content(tradeDto.getContent())
			.status(tradeDto.getStatus() != null ? tradeDto.getStatus() : true)
			.createdAt(tradeDto.getCreatedAt() != null ? tradeDto.getCreatedAt() : LocalDateTime.now())
			.updatedAt(tradeDto.getUpdatedAt())
			.deletedAt(tradeDto.getDeletedAt())
			.build();
	}

	public static TradeDto convertToDto(Trade trade){
		return TradeDto.builder()
			.id(trade.getId())
			.title(trade.getTitle())
			.content(trade.getContent())
			.status(trade.getStatus())
			.createdAt(trade.getCreatedAt())
			.updatedAt(trade.getUpdatedAt())
			.deletedAt(trade.getDeletedAt())
			.build();
	}
}
