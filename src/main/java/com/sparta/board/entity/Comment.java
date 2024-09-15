package com.sparta.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id") //외래키 설정 (상대방 아이디)
    private Board board;

    public Comment(String username, String contents, Board board) {
        this.username = username;
        this.contents = contents;
        this.board = board;
    }

    public void updateComment(String username, String contents) {
        this.username = username;
        this.contents = contents;
    }
}
