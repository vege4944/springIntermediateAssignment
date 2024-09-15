package com.sparta.board.dto.board;

import com.sparta.board.dto.comment.CommentDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BoardWithCommentResponseDto {
    private final Long id;
    private final String username;
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final List<CommentDto> comments;

    public BoardWithCommentResponseDto(Long id, String username, String title, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt, List<CommentDto> comments) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.comments = comments;
    }
}
