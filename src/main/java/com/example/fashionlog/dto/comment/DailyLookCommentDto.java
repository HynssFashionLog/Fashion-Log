package com.example.fashionlog.dto.comment;

import com.example.fashionlog.domain.baseentity.CommentUpdatable;
import com.example.fashionlog.domain.board.DailyLook;
import com.example.fashionlog.domain.comment.DailyLookComment;

import com.example.fashionlog.domain.Member;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link DailyLookComment}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyLookCommentDto implements CommentUpdatable {

    private Long id;
    private Long memberId;
    private Long dailyLookId;
    private String content;
    private Boolean commentStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Override
    public String getContent() {
        return this.content;
    }

    public static DailyLookCommentDto convertToDto(DailyLookComment dailyLookComment) {

        return DailyLookCommentDto.builder()
            .id(dailyLookComment.getId())
            .memberId(dailyLookComment.getMember().getMemberId())
            .dailyLookId(dailyLookComment.getDailyLook().getId())
            .content(dailyLookComment.getContent())
            .commentStatus(dailyLookComment.getCommentStatus())
            .createdAt(dailyLookComment.getCreatedAt())
            .updatedAt(dailyLookComment.getUpdatedAt())
            .deletedAt(dailyLookComment.getDeletedAt())
            .build();
    }

    public static DailyLookComment convertToEntity(DailyLookCommentDto dailyLookCommentDto,
        DailyLook dailyLook, Member member) {

        return DailyLookComment.builder()
            .id(dailyLookCommentDto.getId())
            .member(member)
            .dailyLook(dailyLook)
            .content(dailyLookCommentDto.getContent())
            .commentStatus(dailyLookCommentDto.getCommentStatus())
            .createdAt(dailyLookCommentDto.getCreatedAt())
            .updatedAt(dailyLookCommentDto.getUpdatedAt())
            .deletedAt(dailyLookCommentDto.getDeletedAt())
            .build();
    }
}