package com.example.fashionlog.domain.baseentity;

import com.example.fashionlog.domain.Category;

public interface NoticeUpdatable extends Updatable {
	Category getCategory();
}
