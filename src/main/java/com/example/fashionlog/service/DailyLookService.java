package com.example.fashionlog.service;

import com.example.fashionlog.aop.AuthCheck;
import com.example.fashionlog.aop.AuthorType;
import com.example.fashionlog.domain.DailyLook;
import com.example.fashionlog.domain.DailyLookComment;
import com.example.fashionlog.domain.Member;
import com.example.fashionlog.dto.DailyLookCommentDto;
import com.example.fashionlog.dto.DailyLookDto;
import com.example.fashionlog.repository.DailyLookCommentRepository;
import com.example.fashionlog.repository.DailyLookRepository;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DailyLookService implements BoardService {

    private final DailyLookRepository dailyLookRepository;
    private final DailyLookCommentRepository dailyLookCommentRepository;
    private final CurrentUserProvider currentUserProvider;

    /**
     * DailyLookService 생성자입니다.
     *
     * @param dailyLookRepository 데일리룩 리포지토리
     * @param dailyLookCommentRepository 데일리룩 댓글 리포지토리
     * @param currentUserProvider 현재 사용자 정보 제공자
     */
    public DailyLookService(DailyLookRepository dailyLookRepository,
        DailyLookCommentRepository dailyLookCommentRepository,
        CurrentUserProvider currentUserProvider) {
        this.dailyLookRepository = dailyLookRepository;
        this.dailyLookCommentRepository = dailyLookCommentRepository;
        this.currentUserProvider = currentUserProvider;
    }

    /**
     * 모든 활성화된 데일리룩 게시글을 조회합니다.
     *
     * @return 데일리룩 DTO 리스트
     */
    @Transactional(readOnly = true)
    public List<DailyLookDto> getAllDailyLookPost() {
        List<DailyLook> dailyLookList = dailyLookRepository.findAllByStatusIsTrue();
        return dailyLookList.stream().map(DailyLookDto::convertToDto).collect(Collectors.toList());
    }

    /**
     * 새로운 데일리룩 게시글을 생성합니다.
     *
     * @param dailyLookDto 생성할 데일리룩 정보
     */
    @AuthCheck(value = {"NORMAL", "ADMIN"}, Type = "DailyLook", AUTHOR_TYPE = AuthorType.POST)
    @Transactional
    public void createDailyLookPost(DailyLookDto dailyLookDto) {
        System.out.println("Content in service: " + dailyLookDto.getContent());
        dailyLookDto.setPostStatus(Boolean.TRUE);
        // 현재 로그인된 사용자를 가져옵니다
        Member currentUser = currentUserProvider.getCurrentUser();

        dailyLookDto.setAuthorName(currentUser.getNickname());

        DailyLook dailyLook = DailyLookDto.convertToEntity(dailyLookDto, currentUser);
        dailyLookRepository.save(dailyLook);
    }

    /**
     * 특정 ID의 데일리룩 게시글을 조회합니다.
     *
     * @param id 조회할 데일리룩 게시글 ID
     * @return 데일리룩 DTO
     */
    @Transactional(readOnly = true)
    public DailyLookDto getDailyLookPostById(Long id) {
        DailyLook dailyLook = findDailyLookById(id);
        return DailyLookDto.convertToDto(dailyLook);
    }

    /**
     * 특정 데일리룩 게시글의 모든 댓글을 조회합니다.
     *
     * @param id 데일리룩 게시글 ID
     * @return 데일리룩 댓글 DTO 리스트
     */
    @Transactional(readOnly = true)
    public List<DailyLookCommentDto> getAllDailyLookCommentByDailyLookId(Long id) {
        List<DailyLookComment> DailyLookComments = dailyLookCommentRepository.findAllByDailyLookIdAndCommentStatusIsTrue(id);
        return DailyLookComments.stream().map(DailyLookCommentDto::convertToDto).collect(Collectors.toList());
    }

    /**
     * 데일리룩 게시글을 수정합니다.
     *
     * @param id 수정할 데일리룩 게시글 ID
     * @param dailyLookDto 수정할 데일리룩 정보
     */
    @AuthCheck(value = {"NORMAL", "ADMIN"}, checkAuthor = true, Type = "DailyLook", AUTHOR_TYPE = AuthorType.POST)
    @Transactional
    public void editDailyLookPost(Long id, DailyLookDto dailyLookDto) {
        DailyLook dailyLook = findDailyLookById(id);
        dailyLook.update(dailyLookDto);
    }

    /**
     * 데일리룩 게시글을 삭제합니다.
     *
     * @param id 삭제할 데일리룩 게시글 ID
     */
    @AuthCheck(value = {"NORMAL", "ADMIN"}, checkAuthor = true, Type = "DailyLook", AUTHOR_TYPE = AuthorType.POST)
    @Transactional
    public void deleteDailyLookPost(Long id) {
        DailyLook dailyLook = findDailyLookById(id);
        dailyLook.delete();
    }


    private DailyLook findDailyLookById(Long id) {
        return dailyLookRepository.findByIdAndStatusIsTrue(id)
            .orElseThrow(() -> new EntityNotFoundException("post를 찾지 못했습니다."));
    }

    /**
     * 데일리룩 게시글에 새 댓글을 생성합니다.
     *
     * @param id 댓글을 달 데일리룩 게시글 ID
     * @param dailyLookCommentDto 생성할 댓글 정보
     */
    @AuthCheck(value = {"NORMAL", "ADMIN"}, Type = "DailyLook", AUTHOR_TYPE = AuthorType.COMMENT)
    @Transactional
    public void createDailyLookComment(Long id, DailyLookCommentDto dailyLookCommentDto) {
        dailyLookCommentDto.setId(null);
        dailyLookCommentDto.setDailyLookId(id);

        dailyLookCommentDto.setCommentStatus(Boolean.TRUE);
        dailyLookCommentDto.setCreatedAt(LocalDateTime.now());

        DailyLook dailyLook = findDailyLookById(id);
        Member currentUser = currentUserProvider.getCurrentUser();

        DailyLookComment dailyLookComment = DailyLookCommentDto.convertToEntity(
            dailyLookCommentDto, dailyLook, currentUser);
        dailyLookCommentRepository.save(dailyLookComment);
    }

    /**
     * 데일리룩 댓글을 수정합니다.
     *
     * @param id 수정할 댓글 ID
     * @param dailyLookCommentDto 수정할 댓글 정보
     */
    @AuthCheck(value = {"NORMAL", "ADMIN"}, checkAuthor = true, Type = "DailyLook", AUTHOR_TYPE = AuthorType.COMMENT)
    @Transactional
    public void editDailyLookComment(Long id,
        DailyLookCommentDto dailyLookCommentDto) {
        DailyLookComment dailyLookComment = getDailyLookComment(id);
        dailyLookComment.updateComment(dailyLookCommentDto);
    }

    /**
     * 데일리룩 댓글을 삭제합니다.
     *
     * @param id 삭제할 댓글 ID
     */
    @AuthCheck(value = {"NORMAL", "ADMIN"}, checkAuthor = true, Type = "DailyLook", AUTHOR_TYPE = AuthorType.COMMENT)
    @Transactional
    public void deleteDailyLookComment(Long id) {
        DailyLookComment dailyLookComment = getDailyLookComment(id);
        dailyLookComment.deleteComment();
    }

    /**
     * ID로 데일리룩 댓글을 조회합니다.
     *
     * @param commentId 조회할 댓글 ID
     * @return 데일리룩 댓글 엔티티
     * @throws EntityNotFoundException 댓글을 찾지 못한 경우
     */
    private DailyLookComment getDailyLookComment(Long commentId) {
        return dailyLookCommentRepository.findById(commentId)
            .orElseThrow(() -> new IllegalArgumentException("댓글을 찾기 못했습니다."));
    }

    /**
     * 주어진 게시글 ID의 작성자가 현재 사용자인지 확인합니다.
     *
     * @param postId 확인할 게시글 ID
     * @param memberEmail 현재 사용자의 이메일
     * @return 현재 사용자가 게시글의 작성자인 경우 true, 그렇지 않은 경우 false
     */
    @Override
    public boolean isPostAuthor(Long postId, String memberEmail) {
        return dailyLookRepository.findById(postId)
            .map(dailyLook -> dailyLook.isAuthor(memberEmail))
            .orElse(false);
    }

    /**
     * 주어진 댓글 ID의 작성자가 현재 사용자인지 확인합니다.
     *
     * @param commentId 확인할 게시글 ID
     * @param memberEmail 현재 사용자의 이메일
     * @return 현재 사용자가 게시글의 작성자인 경우 true, 그렇지 않은 경우 false
     */
    @Override
    public boolean isCommentAuthor(Long commentId, String memberEmail) {
        return dailyLookCommentRepository.findById(commentId)
            .map(dailyLook -> dailyLook.isAuthor(memberEmail))
            .orElse(false);
    }
}