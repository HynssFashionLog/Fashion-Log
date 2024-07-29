package com.example.fashionlog.service;

import com.example.fashionlog.domain.DailyLook;
import com.example.fashionlog.domain.DailyLookComment;
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
public class DailyLookService {

    private final DailyLookRepository dailyLookRepository;

    private final DailyLookCommentRepository dailyLookCommentRepository;

    public DailyLookService(DailyLookRepository dailyLookRepository,
        DailyLookCommentRepository dailyLookCommentRepository) {
        this.dailyLookRepository = dailyLookRepository;
        this.dailyLookCommentRepository = dailyLookCommentRepository;
    }

    @Transactional(readOnly = true)
    public List<DailyLookDto> getAllDailyLookPost() {
        List<DailyLook> dailyLookList = dailyLookRepository.findAllByPostStatusIsTrue();
        return dailyLookList.stream().map(DailyLookDto::convertToDto).collect(Collectors.toList());
    }

    @Transactional
    public void createDailyLookPost(DailyLookDto dailyLookDto) {
        System.out.println("Content in service: " + dailyLookDto.getContent());
        dailyLookDto.setPostStatus(Boolean.TRUE);
        DailyLook dailyLook = DailyLookDto.convertToEntity(dailyLookDto);
        dailyLookRepository.save(dailyLook);
    }

    @Transactional(readOnly = true)
    public DailyLookDto getDailyLookPostById(Long id) {
        DailyLook dailyLook = findDailyLookById(id);
        return DailyLookDto.convertToDto(dailyLook);
    }

    @Transactional(readOnly = true)
    public List<DailyLookCommentDto> getAllDailyLookCommentByDailyLookId(Long id) {
        List<DailyLookComment> DailyLookComments = dailyLookCommentRepository.findAllByDailyLookIdAndCommentStatusIsTrue(id);
        return DailyLookComments.stream().map(DailyLookCommentDto::convertToDto).collect(Collectors.toList());
    }

    @Transactional
    public void editDailyLookPost(Long id, DailyLookDto dailyLookDto) {
        DailyLook dailyLook = findDailyLookById(id);
        dailyLook.updateDailyLook(dailyLookDto);
    }

    @Transactional
    public void deleteDailyLookPost(Long id) {
        DailyLook dailyLook = findDailyLookById(id);
        dailyLook.deleteDailyLook();
    }

    private DailyLook findDailyLookById(Long id) {
        return dailyLookRepository.findByIdAndPostStatusIsTrue(id)
            .orElseThrow(() -> new EntityNotFoundException("post를 찾지 못했습니다."));
    }

    @Transactional
    public void createDailyLookComment(Long id, DailyLookCommentDto dailyLookCommentDto) {
        dailyLookCommentDto.setId(null);
        dailyLookCommentDto.setDailyLookId(id);
        dailyLookCommentDto.setCommentStatus(Boolean.TRUE);
        dailyLookCommentDto.setCreatedAt(LocalDateTime.now());

        DailyLook dailyLook = findDailyLookById(id);
        DailyLookComment dailyLookComment = DailyLookCommentDto.convertToEntity(
            dailyLookCommentDto, dailyLook);
        dailyLookCommentRepository.save(dailyLookComment);
    }

    @Transactional
    public void editDailyLookComment(Long postId, Long commentId,
        DailyLookCommentDto dailyLookCommentDto) {
        DailyLook dailyLook = findDailyLookById(postId);
        DailyLookComment dailyLookComment = getDailyLookComment(commentId);
        dailyLookComment.updateDailyLookComment(dailyLookCommentDto, dailyLook);
    }

    @Transactional
    public void deleteDailyLookComment(Long commentId) {
        DailyLookComment dailyLookComment = getDailyLookComment(commentId);
        dailyLookComment.deleteDailyLookComment();
    }

    private DailyLookComment getDailyLookComment(Long commentId) {
        return dailyLookCommentRepository.findById(commentId)
            .orElseThrow(() -> new IllegalArgumentException("댓글을 찾기 못했습니다."));
    }
}