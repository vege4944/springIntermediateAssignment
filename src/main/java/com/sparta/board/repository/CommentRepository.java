package com.sparta.board.repository;

import com.sparta.board.dto.comment.CommentListForBoardResponseDto;
import com.sparta.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllCommentByBoardId(Long boardId);

    List<CommentListForBoardResponseDto> findByBoardId(Long id);

    int countByBoardId(Long id);
}
