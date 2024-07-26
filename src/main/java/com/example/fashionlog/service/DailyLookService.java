package com.example.fashionlog.service;

import com.example.fashionlog.domain.DailyLook;
import com.example.fashionlog.dto.DailyLookDto;
import com.example.fashionlog.repository.DailyLookCommentRepository;
import com.example.fashionlog.repository.DailyLookRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class DailyLookService {

    private final DailyLookRepository dailyLookRepository;

    private final DailyLookCommentRepository dailyLookCommentRepository;

    public DailyLookService(DailyLookRepository dailyLookRepository,
        DailyLookCommentRepository dailyLookCommentRepository) {
        this.dailyLookRepository = dailyLookRepository;
        this.dailyLookCommentRepository = dailyLookCommentRepository;
    }

    public List<DailyLookDto> getAllDailyLookPost() {
        List<DailyLook> dailyLookList = dailyLookRepository.findAll();
        return dailyLookList.stream().map(DailyLookDto::convertToDto).collect(Collectors.toList());
    }

    public void createDailyLookPost(DailyLookDto dailyLookDto) {
        System.out.println("Content in service: " + dailyLookDto.getContent());
        DailyLook dailyLook = DailyLookDto.convertToEntity(dailyLookDto);
        dailyLookRepository.save(dailyLook);
    }

    public DailyLookDto getDailyLookPostById(Long id) {
        DailyLook dailyLook = DailyLookFindById(id);
        return DailyLookDto.convertToDto(dailyLook);
    }

    //추가 코드
    public void editDailyLookPost(Long id, DailyLookDto dailyLookDto) {
        DailyLook dailyLook = DailyLookFindById(id);
        dailyLook.updateDailyLook(dailyLookDto);
        dailyLookRepository.save(dailyLook);
    }

    private DailyLook DailyLookFindById(Long id) {
        return dailyLookRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("게시글을 찾지 못했습니다."));
    }
}