package com.sparta.board.service;

import com.sparta.board.dto.comment.CommentRequestDto;
import com.sparta.board.dto.comment.CommentResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.Comment;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.CommentRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    // 하나의 게시글에 댓글 생성
    @Transactional
    public CommentResponseDto createCommentByBoardId(Long boardId, CommentRequestDto commentRequestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new NullPointerException("찾으시는 글이 없습니다."));

        Comment comment = new Comment(
                commentRequestDto.getUsername(),
                commentRequestDto.getContents(),
                board
        );
        Comment savedComment = commentRepository.save(comment);
        return new CommentResponseDto (
                savedComment.getId(),
                savedComment.getUsername(),
                savedComment.getContents(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt()
        );
    }

    // 특정 댓글 단건 조회
    public CommentResponseDto getDetailCommentByCommentId(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new NullPointerException("찾으시는 댓글이 없습니다."));
        return new CommentResponseDto(
                comment.getId(),
                comment.getUsername(),
                comment.getContents(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    // 하나의 게시글의 댓글 전체 조회
    public List<CommentResponseDto> getAllCommentsByBoardId(Long boardId) {
        List<Comment> commentsList = commentRepository.findAllCommentByBoardId(boardId);

        List<CommentResponseDto> dtoList = new ArrayList<>();
        for (Comment comment : commentsList) {
            CommentResponseDto dto = new CommentResponseDto(
                    comment.getId(),
                    comment.getUsername(),
                    comment.getContents(),
                    comment.getCreatedAt(),
                    comment.getModifiedAt()
            );
            dtoList.add(dto);
        }
        return dtoList;
    }

    // 특정 댓글 수정
    @Transactional
    public CommentResponseDto updateCommentByCommentId(Long commentId, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new NullPointerException("찾으시는 댓글이 없습니다."));
        comment.updateComment(
                commentRequestDto.getUsername(),
                commentRequestDto.getContents()
        );
        return new CommentResponseDto(
                comment.getId(),
                comment.getUsername(),
                comment.getContents(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    // 특정 댓글 삭제
    @Transactional
    public void deleteCommentByCommentId(Long commentId) {
        commentRepository.findById(commentId).orElseThrow(()-> new NullPointerException("찾으시는 댓글이 없습니다."));
        commentRepository.deleteById(commentId);
    }
}
