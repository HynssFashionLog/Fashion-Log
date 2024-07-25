package com.example.fashionlog.dto;

import com.example.fashionlog.domain.DailyLook;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link DailyLook}
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyLookDto {

    Long id;
    String title;
    String content;
    Boolean postStatus;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    LocalDateTime deletedAt;

    public static DailyLookDto convertToDto(DailyLook dailyLook) {
        return DailyLookDto.builder()
            .id(dailyLook.getId())
            .title(dailyLook.getTitle())
            .content(dailyLook.getContent())
            .postStatus(dailyLook.getPostStatus())
            .createdAt(dailyLook.getCreatedAt())
            .updatedAt(dailyLook.getUpdatedAt())
            .deletedAt(dailyLook.getDeletedAt())
            .build();
    }

    public static DailyLook convertToEntity(DailyLookDto dailyLookDto) {
        return DailyLook.builder()
            .id(dailyLookDto.getId())
            .title(dailyLookDto.getTitle())
            .content(dailyLookDto.getContent())
            .postStatus(dailyLookDto.getPostStatus())
            .createdAt(dailyLookDto.getCreatedAt())
            .updatedAt(dailyLookDto.getUpdatedAt())
            .deletedAt(dailyLookDto.getDeletedAt())
            .build();
    }
}