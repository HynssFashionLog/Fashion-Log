package com.example.fashionlog.dto;

import com.example.fashionlog.domain.DailyLook;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for {@link DailyLook}
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyLookDto {

    private Long id;
    private String title;
    private String content;
    private Boolean postStatus = false;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

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
            .createdAt(LocalDateTime.now())
            .updatedAt(dailyLookDto.getUpdatedAt())
            .deletedAt(dailyLookDto.getDeletedAt())
            .build();
    }

    @Override
    public String toString() {
        return "DailyLookDto{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", content='" + content + '\'' +
            ", postStatus=" + postStatus +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            ", deletedAt=" + deletedAt +
            '}';
    }
}