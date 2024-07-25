package com.example.fashionlog.dto;

import com.example.fashionlog.domain.DailyLook;
import com.example.fashionlog.domain.DailyLookComment;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link DailyLookComment}
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyLookCommentDto {

    private Long id;
    private Long dailyLookId;
    private String content;
    private Boolean commentStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public static DailyLookCommentDto convertToDto(DailyLookComment dailyLookComment) {

        return DailyLookCommentDto.builder()
            .id(dailyLookComment.getId())
            .dailyLookId(dailyLookComment.getDailyLookId())
            .content(dailyLookComment.getContent())
            .commentStatus(dailyLookComment.getCommentStatus())
            .createdAt(dailyLookComment.getCreatedAt())
            .updatedAt(dailyLookComment.getUpdatedAt())
            .deletedAt(dailyLookComment.getDeletedAt())
            .build();
    }

    public DailyLookComment convertToEntity(DailyLookCommentDto dailyLookCommentDto) {

        return DailyLookComment.builder()
            .id(dailyLookCommentDto.getId())
            .dailyLookId(dailyLookCommentDto.getDailyLookId())
            .content(dailyLookCommentDto.getContent())
            .commentStatus(dailyLookCommentDto.getCommentStatus())
            .createdAt(dailyLookCommentDto.getCreatedAt())
            .updatedAt(dailyLookCommentDto.getUpdatedAt())
            .deletedAt(dailyLookCommentDto.getDeletedAt())
            .build();
    }
}