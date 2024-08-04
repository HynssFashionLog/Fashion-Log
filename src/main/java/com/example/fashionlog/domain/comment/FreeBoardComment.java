package com.example.fashionlog.domain.comment;

import com.example.fashionlog.domain.baseentity.CommentBaseEntity;
import com.example.fashionlog.domain.board.FreeBoard;
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

/**
 * 자유 게시판 댓글 Entity
 *
 * @author Hynss
 * @version 1.0.0
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "freeboard_comment")
public class FreeBoardComment extends CommentBaseEntity {

	// 댓글 아이디(PK)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "freeboard_comment_id")
	private Long id;

	// 게시물 아이디(FK)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "freeboard_id")
	private FreeBoard freeBoard;
}