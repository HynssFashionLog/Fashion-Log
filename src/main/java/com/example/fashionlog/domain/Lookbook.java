package com.example.fashionlog.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
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