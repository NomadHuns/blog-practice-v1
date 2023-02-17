package shop.mtcoding.blog2.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog2.dto.ResponseDto;
import shop.mtcoding.blog2.dto.board.BoardReq.BoardSaveReqDto;
import shop.mtcoding.blog2.dto.board.BoardReq.BoardUpdateReqDto;
import shop.mtcoding.blog2.dto.board.BoardResp.BoardDetailRespDto;
import shop.mtcoding.blog2.dto.board.BoardResp.BoardListRespDto;
import shop.mtcoding.blog2.dto.reply.ReplyResp.ReplyDetailRespDto;
import shop.mtcoding.blog2.ex.CustomApiException;
import shop.mtcoding.blog2.ex.CustomException;
import shop.mtcoding.blog2.model.Love;
import shop.mtcoding.blog2.model.User;
import shop.mtcoding.blog2.service.BoardService;
import shop.mtcoding.blog2.service.LoveService;
import shop.mtcoding.blog2.service.ReplyService;
import shop.mtcoding.blog2.util.JsoupUtil;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final HttpSession session;
    private final BoardService boardService;
    private final ReplyService replyService;
    private final LoveService loveService;

    @GetMapping({ "/", "/board/list", "/main" })
    public String main(Model model) throws IOException {
        List<BoardListRespDto> boardList = boardService.getBoardList();
        model.addAttribute("boardList", boardList);
        JsoupUtil.stockMarket(model);
        return "board/list";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable("id") int id, Model model) throws IOException {
        BoardDetailRespDto board = boardService.getBoard(id);
        List<ReplyDetailRespDto> replyList = replyService.getReplyList(board.getId());
        User principal = (User) session.getAttribute("principal");
        if (principal != null) {
            Love lovePS = loveService.getLove(id, principal.getId());
            model.addAttribute("love", lovePS);
        }
        model.addAttribute("board", board);
        model.addAttribute("replyList", replyList);
        JsoupUtil.stockMarket(model);
        return "board/detail";
    }

    @GetMapping("/board/writeForm")
    public String writeForm(Model model) throws IOException {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED, "/loginForm");
        }
        JsoupUtil.stockMarket(model);
        return "board/writeForm";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable("id") int id, Model model) throws IOException {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED, "/loginForm");
        }
        BoardDetailRespDto board = boardService.getBoard(id);
        model.addAttribute("board", board);
        JsoupUtil.stockMarket(model);
        return "board/updateForm";
    }

    @PostMapping("/board")
    public String save(BoardSaveReqDto boardSaveReqDto, Model model) throws IOException {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED, "/loginForm");
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

    @DeleteMapping("/board/{id}")
    public @ResponseBody ResponseEntity<?> delete(@PathVariable("id") int id, Model model) throws IOException {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED, "/loginForm");
        }
        boardService.delete(id, principal.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "삭제 성공", null), HttpStatus.OK);
    }

    @PutMapping("/board/{id}")
    public @ResponseBody ResponseEntity<?> update(@PathVariable("id") int id,
            @RequestBody BoardUpdateReqDto boardUpdateReqDto, Model model) throws IOException {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED, "/loginForm");
        }
        if (boardUpdateReqDto.getTitle().isEmpty() || boardUpdateReqDto.getTitle() == null) {
            throw new CustomApiException("제목을 입력하세요");
        }
        if (boardUpdateReqDto.getContent().isEmpty() || boardUpdateReqDto.getContent() == null) {
            throw new CustomApiException("내용을 입력하세요");
        }
        boardService.update(id, principal.getId(), boardUpdateReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "수정 성공", null), HttpStatus.OK);
    }
}
