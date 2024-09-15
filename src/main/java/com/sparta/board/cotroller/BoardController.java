package com.sparta.board.cotroller;

import com.sparta.board.dto.board.BoardNewsFeedDto;
import com.sparta.board.dto.board.BoardRequestDto;
import com.sparta.board.dto.board.BoardResponseDto;
import com.sparta.board.dto.board.BoardWithCommentResponseDto;
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
    public BoardResponseDto createBoard (@RequestBody BoardRequestDto boardRequestDto){
        return boardService.createBoard(boardRequestDto);
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
    public Page<BoardNewsFeedDto> getBoardNewsFeed (
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size
    ){
        return boardService.getBoardNewsFeed(page, size);
    }


    @PutMapping("/{boardId}") // 수정하기
    public BoardResponseDto updateBoardByBoardId (
            @PathVariable Long boardId,
            @RequestBody BoardRequestDto boardRequestDto
    ){
        return boardService.updateBoardByBoardId(boardId, boardRequestDto);
    }

    @DeleteMapping("/{boardId}") // 일정 삭제 시 해당 글의 댓글도 함께 삭제하기
    public void deleteBoard(@PathVariable Long boardId){
        boardService.deleteBoard(boardId);
    }
}
