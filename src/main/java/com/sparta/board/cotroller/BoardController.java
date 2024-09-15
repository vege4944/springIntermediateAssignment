package com.sparta.board.cotroller;

import com.sparta.board.dto.board.BoardRequestDto;
import com.sparta.board.dto.board.BoardResponseDto;
import com.sparta.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public BoardResponseDto getDetailBoardByBoardId (@PathVariable Long boardId){
        return boardService.getDetailBoardByBoardId(boardId);
    }

    @PutMapping("/{boardId}")
    public BoardResponseDto updateBoardByBoardId (
            @PathVariable Long boardId,
            @RequestBody BoardRequestDto boardRequestDto
    ){
        return boardService.updateBoardByBoardId(boardId, boardRequestDto);
    }
}
