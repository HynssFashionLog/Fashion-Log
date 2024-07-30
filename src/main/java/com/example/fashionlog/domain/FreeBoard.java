package com.example.fashionlog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 자유 게시판 Entity
 *
 * @author Hynss
 * @version 1.0.0
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
public class FreeBoard extends BaseEntity {

	// 게시물 아이디(PK)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "freeboard_id")
	private Long id;
}