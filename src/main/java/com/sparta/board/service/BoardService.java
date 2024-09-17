package com.sparta.board.service;

import com.sparta.board.dto.board.*;
import com.sparta.board.dto.comment.CommentListForBoardResponseDto;
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
    public BoardSaveResponseDto createBoard (BoardSaveRequestDto boardSaveRequestDto){
        Board board = new Board(
                boardSaveRequestDto.getUsername(),
                boardSaveRequestDto.getTitle(),
                boardSaveRequestDto.getTitle()
        );
        Board savedBoard = boardRepository.save(board);
        return new BoardSaveResponseDto(
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
        List<CommentListForBoardResponseDto> commentList = commentRepository.findByBoardId(board.getId());

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

    // 일정 전체 조회
    public List<BoardWithCommentResponseDto> getAllBoards() {
        List<Board> boardList = boardRepository.findAll();

        List<BoardWithCommentResponseDto> dtoList = new ArrayList<>();
        for (Board board : boardList) {
            List<CommentListForBoardResponseDto> commentList = commentRepository.findByBoardId(board.getId());
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

    // board 뉴스피드 페이지
    public Page<BoardNewsFeedResponseDto> getBoardNewsFeed(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Board> boardPage = boardRepository.findAllByOrderByModifiedAtDesc(pageable);
        return boardPage.map(board -> new BoardNewsFeedResponseDto(
                board.getId(),
                board.getUsername(),
                board.getTitle(),
                board.getContents(),
                board.getCreatedAt(),
                board.getModifiedAt(),
                commentRepository.countByBoardId(board.getId()) //commentRepository 에서 조회하고 있는 boardId를 가진 총 댓글의 개수를 반환
        ));
    }


    // 일정 수정
    @Transactional
    public BoardUpdateResponseDto updateBoardByBoardId(Long boardId, BoardUpdateRequestDto boardUpdateRequestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new NullPointerException("찾으시는 일정이 없습니다."));
        board.updateBoard(
                boardUpdateRequestDto.getTitle(),
                boardUpdateRequestDto.getContents()
        );
        return new BoardSaveResponseDto(
                board.getId(),
                board.getUsername(),
                board.getTitle(),
                board.getContents(),
                board.getCreatedAt(),
                board.getModifiedAt()
        );
    }

    // 일정 삭제시 해당 글의 댓글도 모두 함께 삭제
    @Transactional
    public void deleteBoard(Long boardId) {
        if (!boardRepository.existsById(boardId)){ // ! = NOT
            throw new NullPointerException("찾으시는 일정이 없습니다.");
        }
        boardRepository.deleteById(boardId);
    }
}
