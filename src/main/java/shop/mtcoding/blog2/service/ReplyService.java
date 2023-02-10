package shop.mtcoding.blog2.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog2.dto.reply.ReplyReq.ReplySaveReqDto;
import shop.mtcoding.blog2.ex.CustomException;
import shop.mtcoding.blog2.model.ReplyRepository;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;

    @Transactional
    public void save(ReplySaveReqDto replySaveReqDto, int pincipalId) {

        int result = replyRepository.insert(replySaveReqDto.getComment(), replySaveReqDto.getBoardId(), pincipalId);
        if (result != 1) {
            throw new CustomException("댓글쓰기 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
