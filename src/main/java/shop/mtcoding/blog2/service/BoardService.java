package shop.mtcoding.blog2.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog2.dto.board.BoardReq.BoardSaveReqDto;
import shop.mtcoding.blog2.dto.board.BoardReq.BoardUpdateReqDto;
import shop.mtcoding.blog2.dto.board.BoardResp.BoardDetailRespDto;
import shop.mtcoding.blog2.dto.board.BoardResp.BoardListRespDto;
import shop.mtcoding.blog2.ex.CustomApiException;
import shop.mtcoding.blog2.ex.CustomException;
import shop.mtcoding.blog2.model.Board;
import shop.mtcoding.blog2.model.BoardRepository;
import shop.mtcoding.blog2.model.UserRepository;
import shop.mtcoding.blog2.util.JsoupUtil;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public void save(BoardSaveReqDto boardSaveReqDto, int userId) {
        String thumbnail = JsoupUtil.thumbnail(boardSaveReqDto.getContent(), "img", "src", "/images/dora.png");

        int result = boardRepository.insert(boardSaveReqDto.getTitle(), boardSaveReqDto.getContent(),
                thumbnail, userId);
        if (result != 1) {
            throw new CustomException("글쓰기 중 문제가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<BoardListRespDto> getBoardList() {
        List<BoardListRespDto> boardList = boardRepository.findAllWithUser();
        return boardList;
    }

    public List<Board> getBoardListAdmin() {
        List<Board> boardPSList = boardRepository.findAll();
        return boardPSList;
    }

    public List<Board> searchBoardList(String searchString) {
        List<Board> boardPSList = boardRepository.findByTitleOrContent(searchString);
        return boardPSList;
    }

    public BoardDetailRespDto getBoard(int id) {
        BoardDetailRespDto dto = boardRepository.findByIdWithUser(id);
        if (dto == null) {
            throw new CustomException("존재하지 않는 게시물입니다");
        }
        return dto;
    }

    @Transactional
    public void delete(int id, int principalId) {
        Board boardPS = boardRepository.findById(id);
        if (boardPS == null) {
            throw new CustomApiException("존재하지 않는 게시물입니다");
        }
        if (boardPS.getUserId() != principalId) {
            if (!userRepository.findById(principalId).getRole().equals("admin")) {
                throw new CustomApiException("해당 게시글을 삭제할 권한이 없습니다", HttpStatus.FORBIDDEN);
            }
        }
        try {
            boardRepository.deleteById(boardPS.getId());
        } catch (Exception e) {
            throw new CustomApiException("서버에 일시적인 문제가 생겼습니다", HttpStatus.INTERNAL_SERVER_ERROR);
            // 로그를 남겨야함 (DB or File)
        }
    }

    @Transactional
    public void update(int id, int userId, BoardUpdateReqDto boardUpdateReqDto) {
        String thumbnail = JsoupUtil.thumbnail(boardUpdateReqDto.getContent(), "img", "src", "/images/dora.png");

        Board boardPS = boardRepository.findById(id);
        if (boardPS == null) {
            throw new CustomApiException("존재하지 않는 게시물입니다");
        }
        if (boardPS.getUserId() != userId) {
            throw new CustomApiException("해당 게시글을 수정할 권한이 없습니다", HttpStatus.FORBIDDEN);
        }
        try {
            boardRepository.updateById(boardPS.getId(), boardUpdateReqDto.getTitle(),
                    thumbnail,
                    boardUpdateReqDto.getContent());
        } catch (Exception e) {
            throw new CustomApiException("서버에 일시적인 문제가 생겼습니다", HttpStatus.INTERNAL_SERVER_ERROR);
            // 로그를 남겨야함 (DB or File)
        }
    }
}
