package com.sparta.board.dto.board;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardNewsFeedDto {
    private final Long id;
    private final String username;
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final int commentCount;

    public BoardNewsFeedDto(Long id, String username, String title, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt, int commentCount) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.commentCount = commentCount;
    }
}
