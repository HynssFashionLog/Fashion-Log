package com.example.fashionlog.domain;

import com.example.fashionlog.dto.InterviewBoardCommentDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "interview_board_comment")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewBoardComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "interview_board_comment_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "interview_board_id")
	private InterviewBoard interviewBoard;

	@Column(nullable = false)
	private String content;

	@Column(nullable = false)
	private Boolean status;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column
	private LocalDateTime updatedAt;

	@Column
	private LocalDateTime deletedAt;

	public void updateInterviewComment(InterviewBoardCommentDto interviewBoardCommentDto) {
		this.content = interviewBoardCommentDto.getContent();
		this.updatedAt = LocalDateTime.now();
	}

	public void deleteInterviewComment() {
		this.status = Boolean.FALSE;
		this.deletedAt = LocalDateTime.now();
	}
}