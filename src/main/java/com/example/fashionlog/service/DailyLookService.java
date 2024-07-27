package com.example.fashionlog.service;

import com.example.fashionlog.domain.DailyLook;
import com.example.fashionlog.domain.DailyLookComment;
import com.example.fashionlog.dto.DailyLookCommentDto;
import com.example.fashionlog.dto.DailyLookDto;
import com.example.fashionlog.repository.DailyLookCommentRepository;
import com.example.fashionlog.repository.DailyLookRepository;

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
        List<DailyLook> dailyLookList = dailyLookRepository.findAll();
        return dailyLookList.stream().map(DailyLookDto::convertToDto).collect(Collectors.toList());
    }

    @Transactional
    public void createDailyLookPost(DailyLookDto dailyLookDto) {
        System.out.println("Content in service: " + dailyLookDto.getContent());
        DailyLook dailyLook = DailyLookDto.convertToEntity(dailyLookDto);
        dailyLookRepository.save(dailyLook);
    }

    @Transactional(readOnly = true)
    public DailyLookDto getDailyLookPostById(Long id) {
        DailyLook dailyLook = DailyLookFindById(id);
        return DailyLookDto.convertToDto(dailyLook);
    }

    @Transactional(readOnly = true)
    public List<DailyLookCommentDto> getAllDailyLookCommentByDailyLookId(Long id) {
        List<DailyLookComment> DailyLookComments = dailyLookCommentRepository.findAllByDailyLookId(id);
        return DailyLookComments.stream().map(DailyLookCommentDto::convertToDto).collect(Collectors.toList());
    }

    @Transactional
    public void editDailyLookPost(Long id, DailyLookDto dailyLookDto) {
        DailyLook dailyLook = DailyLookFindById(id);
        dailyLook.updateDailyLook(dailyLookDto);
    }

    private DailyLook DailyLookFindById(Long id) {
        return dailyLookRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("게시글을 찾지 못했습니다."));
    }

    @Transactional
    public void deleteDailyPost(Long id) {
        dailyLookRepository.deleteById(id);
    }

    @Transactional
    public void createDailyLookComment(Long id, DailyLookCommentDto dailyLookCommentDto) {
        dailyLookCommentDto.setDailyLookId(id);
        dailyLookCommentDto.setCommentStatus(true);
        dailyLookCommentDto.setCreatedAt(LocalDateTime.now());

        DailyLook dailyLook = DailyLookFindById(id);
        DailyLookComment dailyLookComment = DailyLookCommentDto.convertToEntity(
            dailyLookCommentDto, dailyLook);
        dailyLookCommentRepository.save(dailyLookComment);
    }

    @Transactional
    public void editDailyLookComment(Long postId, Long commentId,
        DailyLookCommentDto dailyLookCommentDto) {
        DailyLook dailyLook = DailyLookFindById(postId);
        DailyLookComment dailyLookComment = dailyLookCommentRepository.findById(commentId)
            .orElseThrow(() -> new IllegalArgumentException("댓글을 찾기 못했습니다."));
        dailyLookComment.updateDailyLookComment(dailyLookCommentDto, dailyLook);
        dailyLookCommentRepository.save(dailyLookComment);
    }
}