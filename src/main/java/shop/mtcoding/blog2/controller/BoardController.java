package shop.mtcoding.blog2.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog2.dto.BoardReq.BoardSaveReqDto;
import shop.mtcoding.blog2.ex.CustomException;
import shop.mtcoding.blog2.model.User;
import shop.mtcoding.blog2.service.BoardService;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final HttpSession session;
    private final BoardService boardService;

    @GetMapping({ "/", "/board/list", "/main" })
    public String main() {
        return "board/list";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable("id") int id) {
        return "board/detail";
    }

    @GetMapping("/board/writeForm")
    public String writeForm() {
        return "board/writeForm";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm() {
        return "board/updateForm";
    }

    @PostMapping("/board")
    public String save(BoardSaveReqDto boardSaveReqDto) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }
        if (boardSaveReqDto.getTitle().isEmpty() || boardSaveReqDto.getTitle() == null) {
            throw new CustomException("제목을 입력해주세요.");
        }
        if (boardSaveReqDto.getTitle().length() > 100) {
            throw new CustomException("제목의 길이가 100자 이하여야 합니다.");
        }
        if (boardSaveReqDto.getContent().isEmpty() || boardSaveReqDto.getContent() == null) {
            throw new CustomException("내용을 입력해주세요.");
        }
        boardService.save(boardSaveReqDto, principal.getId());
        return "redirect:/";
    }
}
