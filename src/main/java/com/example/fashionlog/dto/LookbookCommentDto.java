package com.example.fashionlog.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class LookbookCommentDto {

	private Long id;
	private Long lookbookId;
	private String content;
	private String memberId;
	private Boolean commentStatus;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
