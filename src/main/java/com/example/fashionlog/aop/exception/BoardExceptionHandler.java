package com.example.fashionlog.aop.exception;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BoardExceptionHandler {
	String boardType();
	String errorRedirect() default "";
	boolean isComment() default false;
}