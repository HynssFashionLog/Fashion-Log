package com.example.fashionlog.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 자유 게시판 댓글 DTO
 *
 * @author Hynss
 * @version 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FreeBoardCommentDto {

	private Long id;
	private Long freeBoardId;
	private String content;
	private Boolean commentStatus;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;
}