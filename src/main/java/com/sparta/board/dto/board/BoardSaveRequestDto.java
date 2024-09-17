package com.sparta.board.dto.board;

import lombok.Getter;

@Getter
public class BoardSaveRequestDto {
    private String username;
    private String title;
    private String contents;
}
