package com.sparta.board.cotroller;

import com.sparta.board.dto.comment.CommentRequestDto;
import com.sparta.board.dto.comment.CommentResponseDto;
import com.sparta.board.service.CommentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{boardId}/comments")//하나의 게시글에 댓글 생성
    public CommentResponseDto createCommentByBoardId (
            @PathVariable Long boardId,
            @RequestBody CommentRequestDto commentRequestDto){
        return commentService.createCommentByBoardId(boardId, commentRequestDto);
    }

    @GetMapping("/comments/{commentId}") // 특정 댓글 단건 조회
    public CommentResponseDto getDetailCommentByCommentId (@PathVariable Long commentId){
        return commentService.getDetailCommentByCommentId(commentId);
    }

    @GetMapping ("/{boardId}/comments")// 하나의 게시글의 댓글 전체 조회
    public List<CommentResponseDto> getAllCommentsByBoardId (@PathVariable Long boardId){
        return commentService.getAllCommentsByBoardId(boardId);
    }

    @PutMapping("/comments/{commentId}") // 특정 댓글 수정
    public CommentResponseDto updateCommentByCommentId (
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto commentRequestDto
    ){
        return commentService.updateCommentByCommentId (commentId, commentRequestDto);
    }
    @DeleteMapping("/comments/{commentId}") // 특정 댓글 삭제
    public void deleteCommentByCommentId (@PathVariable Long commentId){
        commentService.deleteCommentByCommentId(commentId);
    }
}
