package com.example.fashionlog.dto;

import com.example.fashionlog.domain.DailyLook;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for {@link DailyLook}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyLookDto {

    Long Id;
    Long memberId;
    String title;
    String content;
    Boolean postStatus;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    LocalDateTime deletedAt;

    public DailyLookDto convertToDto(DailyLook dailyLook) {
        DailyLookDto dailyLookDto = new DailyLookDto();
        dailyLookDto.setId(dailyLook.getId());
        dailyLookDto.setTitle(dailyLook.getTitle());
        dailyLookDto.setContent(dailyLook.getContent());
        dailyLookDto.setPostStatus(dailyLook.getPostStatus());
        dailyLookDto.setCreatedAt(dailyLook.getCreatedAt());
        dailyLookDto.setUpdatedAt(dailyLook.getUpdatedAt());
        dailyLookDto.setDeletedAt(dailyLook.getDeletedAt());
        return dailyLookDto;
    }

    public DailyLook convertToEntity(DailyLookDto dailyLookDto) {
        DailyLook dailyLook = new DailyLook();
        dailyLook.setId(dailyLookDto.getId());
        dailyLook.setTitle(dailyLookDto.getTitle());
        dailyLook.setContent(dailyLookDto.getContent());
        dailyLook.setPostStatus(dailyLookDto.getPostStatus());
        dailyLook.setCreatedAt(dailyLookDto.getCreatedAt());
        dailyLook.setUpdatedAt(dailyLookDto.getUpdatedAt());
        dailyLook.setDeletedAt(dailyLookDto.getDeletedAt());
        return dailyLook;
    }
}