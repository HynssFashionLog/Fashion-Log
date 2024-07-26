package com.example.fashionlog.domain;

import com.example.fashionlog.dto.InterviewBoardDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "interview_board")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE interview_board SET status = false, deleted_at = CURRENT_TIMESTAMP(6) WHERE interview_board_id = ?")
public class InterviewBoard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "interview_board_id")
	private Long id;

	@Column(nullable = false)
	private String title;

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

	public void updateInterviewBoard(InterviewBoardDto interviewBoardDto) {
		this.title = interviewBoardDto.getTitle();
		this.content = interviewBoardDto.getContent();
		this.updatedAt = LocalDateTime.now();
	}
}
