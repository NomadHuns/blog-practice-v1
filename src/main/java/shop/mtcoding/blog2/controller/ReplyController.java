package shop.mtcoding.blog2.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog2.dto.reply.ReplyReq.ReplySaveReqDto;
import shop.mtcoding.blog2.ex.CustomException;
import shop.mtcoding.blog2.model.User;
import shop.mtcoding.blog2.service.ReplyService;

@Controller
@RequiredArgsConstructor
public class ReplyController {
    private final HttpSession session;
    private final ReplyService replyService;

    @PostMapping("/reply")
    public String save(ReplySaveReqDto replySaveReqDto) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("해당 기능은 로그인이 필요합니다", HttpStatus.UNAUTHORIZED);
        }
        if (replySaveReqDto.getComment().isEmpty() || replySaveReqDto.getComment() == null) {
            throw new CustomException("댓글 내용을 입력하세요");
        }
        replyService.save(replySaveReqDto, principal.getId());
        return "redirect:/board/" + replySaveReqDto.getBoardId();
    }
}
