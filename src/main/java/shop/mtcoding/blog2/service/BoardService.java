package shop.mtcoding.blog2.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog2.dto.board.BoardReq.BoardSaveReqDto;
import shop.mtcoding.blog2.dto.board.BoardResp.BoardDetailRespDto;
import shop.mtcoding.blog2.dto.board.BoardResp.BoardListRespDto;
import shop.mtcoding.blog2.ex.CustomException;
import shop.mtcoding.blog2.model.BoardRepository;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public void save(BoardSaveReqDto boardSaveReqDto, int userId) {
        int result = boardRepository.insert(boardSaveReqDto.getTitle(), boardSaveReqDto.getContent(), userId);
        if (result != 1) {
            throw new CustomException("content를 입력하세요.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<BoardListRespDto> getBoardList() {
        List<BoardListRespDto> boardListRespDtoList = boardRepository.findAllWithUser();
        return boardListRespDtoList;
    }

    public BoardDetailRespDto getBoardDetail(int id) {
        BoardDetailRespDto board = boardRepository.findByIdWithUser(id);
        return board;
    }
}
