package com.sparta.board.cotroller;

import com.sparta.board.dto.board.*;
import com.sparta.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    @PostMapping//일정 저장
    public BoardSaveResponseDto createBoard (@RequestBody BoardSaveRequestDto boardSaveRequestDto){
        return boardService.createBoard(boardSaveRequestDto);
    }

    @GetMapping("/{boardId}") // 단건 조회
    public BoardWithCommentResponseDto getDetailBoardByBoardId (@PathVariable Long boardId){
        return boardService.getDetailBoardByBoardId(boardId);
    }

    @GetMapping // 전체 조회
    public List<BoardWithCommentResponseDto> getAllBoards (){
        return boardService.getAllBoards();
    }

    @GetMapping ("/newsfeed")// 뉴스피드 조회
    public Page<BoardNewsFeedResponseDto> getBoardNewsFeed (
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size
    ){
        return boardService.getBoardNewsFeed(page, size);
    }


    @PutMapping("/{boardId}") // 수정하기
    public BoardUpdateResponseDto updateBoardByBoardId (
            @PathVariable Long boardId,
            @RequestBody BoardUpdateRequestDto boardUpdateRequestDto
    ){
        return boardService.updateBoardByBoardId(boardId, boardUpdateRequestDto);
    }

    @DeleteMapping("/{boardId}") // 일정 삭제 시 해당 글의 댓글도 함께 삭제하기
    public void deleteBoard(@PathVariable Long boardId){
        boardService.deleteBoard(boardId);
    }
}
