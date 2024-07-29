package com.example.fashionlog.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 자유 게시판 DTO
 *
 * @author Hynss
 * @version 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FreeBoardDto {

	private Long id;
	private String title;
	private String content;
	private Boolean postStatus;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;
}