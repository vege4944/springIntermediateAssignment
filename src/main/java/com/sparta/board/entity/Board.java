package com.sparta.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor

public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String title;
    private String contents;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public Board(String username, String title, String contents) {
        this.username = username;
        this.title = title;
        this.contents = contents;
    }

    public void updateBoard(String username, String title, String contents) {
        this.username = username;
        this.title = title;
        this.contents = contents;
    }
}
