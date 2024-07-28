package com.example.fashionlog.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "lookbook_comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LookbookComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lookbook_id", nullable = false)
	private Lookbook lookbook;

	@Column(name = "member_id", nullable = false)
	private String memberId;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;

	@Column(name = "comment_status", nullable = false)
	private Boolean commentStatus;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}
}