package com.example.fashionlog.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class LookbookDto {

	private Long id;
	private String memberId;
	private String title;
	private String content;
	private boolean postStatus;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}