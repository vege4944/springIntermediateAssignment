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
//    private String username; (5단계 과제하면서 바꾸고 있는 것들 나중에 확인하기 위해 지우지 않고 주석처리)
    private String title;
    private String contents;

    // 5단계 Board 와 User 의 연관관계 설정 = N : 1 설정 (하나의 유저는 여러 할 일을 작성할 수 있음)
    // User entity 만든 후 Board 와 연관관계 양방향 설정 - User < Board (유저보다 게시글이 많으니까 @ManyToOne이 여기)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE) // 영속성 전이 기능
    private List<Comment> comments = new ArrayList<>();

    public Board(User user, String title, String contents) {
//        this.username = username;
        this.user = user;
        this.title = title;
        this.contents = contents;
    }

    public void updateBoard(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
