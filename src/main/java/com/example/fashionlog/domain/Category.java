package com.example.fashionlog.domain;

import lombok.Getter;

@Getter
public enum Category {
	ALL("전체"),
	DAILY_LOOK("데일리룩게시판"),
	FREE("자유게시판"),
	INTERVIEW("인터뷰게시판"),
	LOOKBOOK("룩북게시판"),
	TRADE("거래게시판");

	private final String displayName;

	Category(String displayName) {
		this.displayName = displayName;
	}

}