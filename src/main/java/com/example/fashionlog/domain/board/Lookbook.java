package com.example.fashionlog.domain.board;

import com.example.fashionlog.domain.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "lookbooks")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Lookbook extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
}