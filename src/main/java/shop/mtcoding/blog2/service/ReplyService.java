package shop.mtcoding.blog2.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.mtcoding.blog2.dto.reply.ReplyReq.ReplySaveReqDto;
import shop.mtcoding.blog2.dto.reply.ReplyResp.ReplyDetailAdminRespDto;
import shop.mtcoding.blog2.dto.reply.ReplyResp.ReplyDetailRespDto;
import shop.mtcoding.blog2.ex.CustomApiException;
import shop.mtcoding.blog2.ex.CustomException;
import shop.mtcoding.blog2.model.Board;
import shop.mtcoding.blog2.model.BoardRepository;
import shop.mtcoding.blog2.model.Reply;
import shop.mtcoding.blog2.model.ReplyRepository;
import shop.mtcoding.blog2.model.UserRepository;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

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

    public List<ReplyDetailAdminRespDto> getReplyListAdmin() {
        List<ReplyDetailAdminRespDto> replyDto = replyRepository.findAllWithUser();
        return replyDto;
    }

    @Transactional(readOnly = true)
    public List<ReplyDetailAdminRespDto> searchBySearchString(String searchString) {
        List<ReplyDetailAdminRespDto> replyPSList = replyRepository.findByLikeCommentOrUsernameWithUser(searchString);
        return replyPSList;
    }

    @Transactional
    public void delete(int replyId, Integer principalId) {
        Reply replyPS = replyRepository.findById(replyId);
        if (replyPS == null) {
            throw new CustomApiException("댓글이 존재하지 않습니다");
        }
        if (replyPS.getUserId() != principalId) {
            if (!userRepository.findById(principalId).getRole().equals("admin")) {
                throw new CustomApiException("댓글을 삭제할 권한이 없습니다", HttpStatus.FORBIDDEN);
            }
        }
        try {
            replyRepository.deleteById(replyId);
        } catch (Exception e) {
            log.error("서버에러 : " + e.getMessage());
            throw new CustomApiException("댓글 삭제 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
