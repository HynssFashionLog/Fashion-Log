package com.example.fashionlog.domain.comment;

import com.example.fashionlog.domain.baseentity.CommentBaseEntity;
import com.example.fashionlog.domain.board.Lookbook;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "lookbook_comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class LookbookComment extends CommentBaseEntity {

	// 댓글 아이디(PK)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lookbook_comment_id")
	private Long id;

	// 게시물 아이디(FK)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lookbook_id", nullable = false)
	private Lookbook lookbook;
}