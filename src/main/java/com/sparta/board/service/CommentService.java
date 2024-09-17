package com.sparta.board.service;

import com.sparta.board.dto.comment.*;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.Comment;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.CommentRepository;
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
    public CommentSaveResponseDto createCommentByBoardId(Long boardId, CommentSaveRequestDto commentSaveRequestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new NullPointerException("찾으시는 글이 없습니다."));

        Comment comment = new Comment(
                commentSaveRequestDto.getUsername(),
                commentSaveRequestDto.getContents(),
                board
        );
        Comment savedComment = commentRepository.save(comment);
        return new CommentSaveResponseDto(
                savedComment.getId(),
                savedComment.getUsername(),
                savedComment.getContents(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt()
        );
    }

    // 특정 댓글 단건 조회
    public CommentGetResponseDto getDetailCommentByCommentId(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new NullPointerException("찾으시는 댓글이 없습니다."));
        return new CommentGetResponseDto(
                comment.getId(),
                comment.getUsername(),
                comment.getContents(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    // 하나의 게시글의 댓글 전체 조회
    public List<CommentGetResponseDto> getAllCommentsByBoardId(Long boardId) {
        List<Comment> commentsList = commentRepository.findAllCommentByBoardId(boardId);

        List<CommentGetResponseDto> dtoList = new ArrayList<>();
        for (Comment comment : commentsList) {
            CommentGetResponseDto dto = new CommentGetResponseDto(
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
    public CommentUpdateResponseDto updateCommentByCommentId(Long commentId, CommentUpdateRequestDto commentUpdateRequestDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new NullPointerException("찾으시는 댓글이 없습니다."));
        comment.updateComment(
                commentUpdateRequestDto.getUsername(),
                commentUpdateRequestDto.getContents()
        );
        return new CommentUpdateResponseDto(
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
