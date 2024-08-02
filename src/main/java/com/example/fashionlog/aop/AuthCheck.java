package com.example.fashionlog.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 권한 설정 어노테이션
 *
 * @author Hynss
 * @version 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {

	String[] value(); // 권한(ADMIN, NORMAL, BANNED) 리스트
}