package com.example.fashionlog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "notice")
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Notice extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notice_id")
	private Long id;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Category category;

	@Override
	public <T extends Updatable> void update(T dto) {
		super.update(dto);
		if (dto instanceof NoticeUpdatable) {
			updateNotice((NoticeUpdatable) dto);
		}
	}

	// NoticeUpdatable을 위한 새로운 update 메서드
	private void updateNotice(NoticeUpdatable dto) {
		if (dto.getCategory() != null) {
			this.category = dto.getCategory();
		}
	}
}
