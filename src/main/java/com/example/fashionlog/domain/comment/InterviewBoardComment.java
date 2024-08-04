package com.example.fashionlog.domain.comment;

import com.example.fashionlog.domain.baseentity.CommentBaseEntity;
import com.example.fashionlog.domain.board.InterviewBoard;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "interview_board_comment")
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewBoardComment extends CommentBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "interview_board_comment_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "interview_board_id")
	private InterviewBoard interviewBoard;

}