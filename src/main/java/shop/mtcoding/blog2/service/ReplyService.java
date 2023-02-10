package shop.mtcoding.blog2.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog2.dto.reply.ReplyReq.ReplySaveReqDto;
import shop.mtcoding.blog2.dto.reply.ReplyResp.ReplyDetailRespDto;
import shop.mtcoding.blog2.ex.CustomException;
import shop.mtcoding.blog2.model.Board;
import shop.mtcoding.blog2.model.BoardRepository;
import shop.mtcoding.blog2.model.ReplyRepository;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void save(ReplySaveReqDto replySaveReqDto, int pincipalId) {
        Board boardPS = boardRepository.findById(replySaveReqDto.getBoardId());
        if (boardPS == null) {
            throw new CustomException("존재하지 않는 게시물입니다");
        }
        int result = replyRepository.insert(replySaveReqDto.getComment(), replySaveReqDto.getBoardId(), pincipalId);
        if (result != 1) {
            throw new CustomException("댓글쓰기 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<ReplyDetailRespDto> getReplyList(int Boardid) {
        List<ReplyDetailRespDto> replyDto = replyRepository.findByBoardIdWithUser(Boardid);
        return replyDto;
    }
}
