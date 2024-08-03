package com.example.fashionlog.aop;

import com.example.fashionlog.domain.Member;
import com.example.fashionlog.service.CurrentUserProvider;
import com.example.fashionlog.service.DailyLookService;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import org.springframework.stereotype.Component;

/**
 * API 권한 검증 Aspect
 *
 * @author Hynss
 * @version 1.0.0
 */
@Aspect
@Component
public class AuthorizationAspect {

    private final CurrentUserProvider currentUserProvider;

    private final DailyLookService dailyLookService;

    /**
     * AuthorizationAspect 생성자
     *
     * @param currentUserProvider 현재 사용자 정보를 제공하는 서비스
     */
    public AuthorizationAspect(CurrentUserProvider currentUserProvider,
        DailyLookService dailyLookService) {
        this.currentUserProvider = currentUserProvider;
        this.dailyLookService = dailyLookService;
    }


    /**
     * AuthCheck 어노테이션이 적용된 메서드에 대한 권한을 검사합니다. 사용자의 역할과 필요시 작성자 권한을 확인합니다.
     *
     * @param joinPoint AOP의 JOINPOINT 객체. 메서드 실행 시점의 정보를 담고 있습니다.
     * @param authCheck 메서드에 적용된 AuthCheck 어노테이션 객체.
     * @throws SecurityException 인증 정보가 올바르지 않거나 사용자에게 필요한 권한이 없는 경우
     */
    @Before("@annotation(authCheck)")
    public void checkPermission(JoinPoint joinPoint, AuthCheck authCheck) {
        // 현재 인증된 사용자의 정보를 가져옴
        Member currentUser = currentUserProvider.getCurrentUser();
        if (currentUser == null) {
            throw new SecurityException("인증 정보가 올바르지 않습니다.");
        }

        boolean hasPermission = checkRolePermission(currentUser, authCheck.value());

        // AuthCheck 어노테이션에 지정된 모든 역할에 대해 검사
        if (authCheck.checkAuthor()) {
            Long id = extractId(joinPoint, authCheck.idParam());
            String type = authCheck.Type();

            hasPermission = hasPermission && (
                authCheck.AUTHOR_TYPE() == AuthorType.POST
                    ? checkPostAuthor(type, id, currentUser.getEmail())
                    : checkCommentAuthor(type, id, currentUser.getEmail())
            );

        }

        // 권한이 없으면 SecurityException 발생
        if (!hasPermission) {
            throw new SecurityException("권한이 없습니다.");
        }
    }

    /**
     * 사용자의 역할이 허용된 역할 목록에 포함되는지 확인합니다.
     *
     * @param user  현재 사용자
     * @param roles 허용된 역할 목록
     * @return 사용자의 역할이 허용된 경우 true, 그렇지 않은 경우 false
     */
    private boolean checkRolePermission(Member user, String[] roles) {
        for (String role : roles) {
            if (user.getRole().name().equals(role)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 주어진 게시판 타입에 따라 사용자가 게시글의 작성자인지 확인합니다. 다른 게시판 추후 구현
     *
     * @param boardType   확인하고자 하는 게시판의 타입
     * @param postId      확인하고자 하는 게시글의 ID
     * @param memberEmail 확인하고자 하는 사용자의 이메일
     * @return 사용자가 해당 개시글의 작성자인경우 true, 그렇지 않은경우 false
     * @throws IllegalArgumentException 지원하지 않는 게시판 타입이 제공된 경우
     */
    private boolean checkPostAuthor(String boardType, Long postId, String memberEmail) {
        return switch (boardType) {
            case "DailyLook" -> dailyLookService.isPostAuthor(postId, memberEmail);
            default -> throw new IllegalArgumentException("알수 없는 보드타입: " + boardType);
        };
    }

    private boolean checkCommentAuthor(String boardType, Long commentId, String memberEmail) {
        return switch (boardType) {
            case "DailyLook" -> dailyLookService.isCommentAuthor(commentId, memberEmail);
            default -> throw new IllegalArgumentException("알수 없는 보드타입: " + boardType);
        };
    }

    /**
     * JoinPoint에서 특정 이름을 가진 Long 타입의 파라미터 값을 추출합니다.
     *
     * @param joinPoint   AOP의 JoinPoint 객체. 메소드 실행 시점의 정보를 담고 있습니다.
     * @param idParamName 찾고자 하는 파라미터의 이름
     * @return 찾은 파라미터의 Long 값
     * @throws IllegalArgumentException 지정된 이름의 Long 타입 파라미터를 찾지 못한 경우
     */
    private Long extractId(JoinPoint joinPoint, String idParamName) {
        // joinPoint 에서 메서드 시그니처 정보를 추출합니다.
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        // 메서드 파라미터 이름 배열을 가져옵니다.
        String[] parameterNames = signature.getParameterNames();

        // 메서드 호출 시 전달된 실제 인자 값들을 가져옵니다.
        Object[] args = joinPoint.getArgs();

        // 파라미터 이름과 값을 순회하면서 찾고자 하는 파라미터를 검색합니다.
        for (int i = 0; i < parameterNames.length; i++) {
            if (idParamName.equals(parameterNames[i]) && args[i] instanceof Long) {
                return (Long) args[i];
            }
        }
        String methodName = signature.getMethod().getName();
        String availableParams = String.join(", ", parameterNames);
        throw new IllegalArgumentException(
            String.format("게시글 ID를 찾을 수 없습니다. 메서드: %s, 찾는 파라미터: %s, 사용 가능한 파라미터: %s",
                methodName, idParamName, availableParams));
    }
}