package com.example.fashionlog.dto;

import com.example.fashionlog.domain.DailyLook;

import com.example.fashionlog.domain.Updatable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link DailyLook}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyLookDto implements Updatable {

    private Long id;
    private String title;
    private String content;
    private Boolean postStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    public static DailyLookDto convertToDto(DailyLook dailyLook) {
        return DailyLookDto.builder()
            .id(dailyLook.getId())
            .title(dailyLook.getTitle())
            .content(dailyLook.getContent())
            .postStatus(dailyLook.getStatus())
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
            .status(dailyLookDto.getPostStatus())
            .createdAt(LocalDateTime.now())
            .updatedAt(dailyLookDto.getUpdatedAt())
            .deletedAt(dailyLookDto.getDeletedAt())
            .build();
    }
}