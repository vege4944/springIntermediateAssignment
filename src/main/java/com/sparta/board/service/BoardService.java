package com.sparta.board.service;

import com.sparta.board.dto.board.BoardNewsFeedDto;
import com.sparta.board.dto.board.BoardRequestDto;
import com.sparta.board.dto.board.BoardResponseDto;
import com.sparta.board.dto.board.BoardWithCommentResponseDto;
import com.sparta.board.dto.comment.CommentDto;
import com.sparta.board.entity.Board;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional // 일정 저장
    public BoardResponseDto createBoard (BoardRequestDto boardRequestDto){
        Board board = new Board(
                boardRequestDto.getUsername(),
                boardRequestDto.getTitle(),
                boardRequestDto.getTitle()
        );
        Board savedBoard = boardRepository.save(board);
        return new BoardResponseDto(
                savedBoard.getId(),
                savedBoard.getUsername(),
                savedBoard.getTitle(),
                savedBoard.getContents(),
                savedBoard.getCreatedAt(),
                savedBoard.getModifiedAt()
        );
    }

    public BoardWithCommentResponseDto getDetailBoardByBoardId(Long boardId) { // 일정 단건 조회
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new NullPointerException("찾으시는 일정이 없습니다."));
        List<CommentDto> commentList = commentRepository.findByBoardId(board.getId());

        return new BoardWithCommentResponseDto(
                board.getId(),
                board.getUsername(),
                board.getTitle(),
                board.getContents(),
                board.getCreatedAt(),
                board.getModifiedAt(),
                commentList
        );
    }

    public List<BoardWithCommentResponseDto> getAllBoards() {
        List<Board> boardList = boardRepository.findAll();

        List<BoardWithCommentResponseDto> dtoList = new ArrayList<>();
        for (Board board : boardList) {
            List<CommentDto> commentList = commentRepository.findByBoardId(board.getId());
            BoardWithCommentResponseDto dto = new BoardWithCommentResponseDto(
                    board.getId(),
                    board.getUsername(),
                    board.getTitle(),
                    board.getContents(),
                    board.getCreatedAt(),
                    board.getModifiedAt(),
                    commentList
            );
            dtoList.add(dto);
        }
        return dtoList;
    }

    // board newsfeed page
    public Page<BoardNewsFeedDto> getBoardNewsFeed(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Board> boardPage = boardRepository.findAllByOrderByModifiedAtDesc(pageable);
        return boardPage.map(board -> new BoardNewsFeedDto(
                board.getId(),
                board.getUsername(),
                board.getTitle(),
                board.getContents(),
                board.getCreatedAt(),
                board.getModifiedAt(),
                commentRepository.countByBoardId(board.getId()) //commentRepository 에서 조회하고 있는 boardId를 가진 총 댓글의 개수를 반환
        ));
    }



    @Transactional
    public BoardResponseDto updateBoardByBoardId(Long boardId, BoardRequestDto boardRequestDto) { // 일정 수정
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new NullPointerException("찾으시는 일정이 없습니다."));
        board.updateBoard(
                boardRequestDto.getUsername(),
                boardRequestDto.getTitle(),
                boardRequestDto.getContents()
        );
        return new BoardResponseDto(
                board.getId(),
                board.getUsername(),
                board.getTitle(),
                board.getContents(),
                board.getCreatedAt(),
                board.getModifiedAt()
        );
    }

}
