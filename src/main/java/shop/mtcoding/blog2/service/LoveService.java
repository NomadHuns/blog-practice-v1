package shop.mtcoding.blog2.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog2.ex.CustomApiException;
import shop.mtcoding.blog2.model.BoardRepository;
import shop.mtcoding.blog2.model.Love;
import shop.mtcoding.blog2.model.LoveRepository;

@Service
@RequiredArgsConstructor
public class LoveService {
    private final LoveRepository loveRepository;
    private final BoardRepository boardRepository;

    public Love getLove(int boardId, int pincipalId) {
        Love loveTemp = new Love(boardId, pincipalId);
        return loveRepository.findByBoardIdAndUserId(loveTemp);
    }

    public void doLove(int boardId, int pincipalId) {
        Love loveTemp = new Love(boardId, pincipalId);
        if (boardRepository.findById(boardId) == null) {
            throw new CustomApiException("존재하지 않는 게시물 입니다");
        }
        if (loveRepository.findByBoardIdAndUserId(loveTemp) != null) {
            throw new CustomApiException("이미 좋아요한 게시물 입니다");
        }
        try {
            loveRepository.insert(loveTemp);
        } catch (Exception e) {
            throw new CustomApiException("서버 오류 : 좋아요 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteLove(int boardId, Integer pincipalId) {
        Love loveTemp = new Love(boardId, pincipalId);
        if (boardRepository.findById(boardId) == null) {
            throw new CustomApiException("존재하지 않는 게시물 입니다");
        }
        Love lovePS = loveRepository.findByBoardIdAndUserId(loveTemp);
        try {
            loveRepository.deleteById(lovePS.getId());
        } catch (Exception e) {
            throw new CustomApiException("서버 오류 : 좋아요 취소 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<Love> findByBoardId(int boardId) {
        List<Love> LovePSList = loveRepository.findByBoardId(boardId);
        return LovePSList;
    }
}
