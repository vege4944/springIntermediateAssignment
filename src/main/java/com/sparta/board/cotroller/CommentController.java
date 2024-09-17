package com.sparta.board.cotroller;

import com.sparta.board.dto.comment.*;
import com.sparta.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{boardId}/comments")//하나의 게시글에 댓글 생성
    public CommentSaveResponseDto createCommentByBoardId (
            @PathVariable Long boardId,
            @RequestBody CommentSaveRequestDto commentSaveRequestDto){
        return commentService.createCommentByBoardId(boardId, commentSaveRequestDto);
    }

    @GetMapping("/comments/{commentId}") // 특정 댓글 단건 조회
    public CommentGetResponseDto getDetailCommentByCommentId (@PathVariable Long commentId){
        return commentService.getDetailCommentByCommentId(commentId);
    }

    @GetMapping ("/{boardId}/comments")// 하나의 게시글의 댓글 전체 조회
    public List<CommentGetResponseDto> getAllCommentsByBoardId (@PathVariable Long boardId){
        return commentService.getAllCommentsByBoardId(boardId);
    }

    @PutMapping("/comments/{commentId}") // 특정 댓글 수정
    public CommentUpdateResponseDto updateCommentByCommentId (
            @PathVariable Long commentId,
            @RequestBody CommentUpdateRequestDto commentUpdateRequestDto
    ){
        return commentService.updateCommentByCommentId (commentId, commentUpdateRequestDto);
    }
    @DeleteMapping("/comments/{commentId}") // 특정 댓글 삭제
    public void deleteCommentByCommentId (@PathVariable Long commentId){
        commentService.deleteCommentByCommentId(commentId);
    }
}
