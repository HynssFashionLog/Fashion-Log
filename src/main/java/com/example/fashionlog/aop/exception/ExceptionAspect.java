package com.example.fashionlog.aop.exception;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionAspect {

	@Around("@annotation(BoardExceptionHandler)")
	public Object handleBoardException(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			return joinPoint.proceed();
		} catch (IllegalArgumentException | SecurityException e) {
			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			BoardExceptionHandler annotation = signature.getMethod()
				.getAnnotation(BoardExceptionHandler.class);
			String boardType = annotation.boardType();
			String errorRedirect = annotation.errorRedirect();
			boolean isComment = annotation.isComment();

			if (!errorRedirect.isEmpty()) {
				return errorRedirect;
			}

			if (isComment) {
				Object[] args = joinPoint.getArgs();
				Long postId = (Long) args[0];
				return "redirect:/fashionlog/" + boardType + "/" + postId;
			}

			String methodName = signature.getName();
			if (methodName.contains("save")) {
				return "redirect:/fashionlog/" + boardType + "/new";
			} else if (methodName.contains("edit")) {
				Object[] args = joinPoint.getArgs();
				Long id = (Long) args[0];
				return "redirect:/fashionlog/" + boardType + "/" + id + "/edit";
			} else {
				return "redirect:/fashionlog/" + boardType;
			}
		}
	}
}