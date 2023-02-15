package shop.mtcoding.blog2.controller;

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

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog2.dto.ResponseDto;
import shop.mtcoding.blog2.dto.reply.ReplyResp.ReplyDetailAdminRespDto;
import shop.mtcoding.blog2.dto.user.UserReq.LoginReqDto;
import shop.mtcoding.blog2.ex.CustomException;
import shop.mtcoding.blog2.model.Board;
import shop.mtcoding.blog2.model.User;
import shop.mtcoding.blog2.service.BoardService;
import shop.mtcoding.blog2.service.ReplyService;
import shop.mtcoding.blog2.service.UserService;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final HttpSession session;
    private final UserService userService;
    private final BoardService boardService;
    private final ReplyService replyService;

    @GetMapping({ "/admin", "/admin/user" })
    public String user(Model model) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("로그인이 필요합니다", HttpStatus.UNAUTHORIZED, "/admin/loginForm");
        }
        if (!principal.getRole().equals("admin")) {
            throw new CustomException("권한이 없습니다", HttpStatus.FORBIDDEN, "/");
        }
        List<User> userPSList = userService.findAll();
        model.addAttribute("userList", userPSList);
        return "admin/user";
    }

    @GetMapping("/admin/board")
    public String board(Model model) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("로그인이 필요합니다", HttpStatus.UNAUTHORIZED, "/admin/loginForm");
        }
        if (!principal.getRole().equals("admin")) {
            throw new CustomException("권한이 없습니다", HttpStatus.FORBIDDEN, "/");
        }
        List<Board> boardPSList = boardService.getBoardListAdmin();
        model.addAttribute("boardList", boardPSList);
        return "admin/board";
    }

    @GetMapping("/admin/board?searchString={searchString}")
    public String searchBoard(@PathVariable("searchString") String searchString, Model model) {
        List<Board> boardPSList = boardService.searchBoardList(searchString);
        model.addAttribute("boardList", boardPSList);
        return "admin/board";
    }

    @GetMapping("/admin/reply")
    public String reply(Model model) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("로그인이 필요합니다", HttpStatus.UNAUTHORIZED, "/admin/loginForm");
        }
        if (!principal.getRole().equals("admin")) {
            throw new CustomException("권한이 없습니다", HttpStatus.FORBIDDEN, "/");
        }
        List<ReplyDetailAdminRespDto> replyPSList = replyService.getReplyListAdmin();
        model.addAttribute("replyList", replyPSList);
        return "admin/reply";
    }

    @GetMapping("/admin/loginForm")
    public String loginForm() {
        return "admin/loginForm";
    }

    @PostMapping("/admin/login")
    public String login(LoginReqDto loginReqDto) {
        if (loginReqDto.getUsername().isEmpty() || loginReqDto.getUsername() == null) {
            throw new CustomException("유저이름을 입력하세요.");
        }
        if (loginReqDto.getPassword().isEmpty() || loginReqDto.getPassword() == null) {
            throw new CustomException("패스워드를 입력하세요.");
        }
        User principal = userService.login(loginReqDto);
        session.setAttribute("principal", principal);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("로그인이 필요합니다", HttpStatus.UNAUTHORIZED, "/admin/loginForm");
        }
        if (!principal.getRole().equals("admin")) {
            throw new CustomException("권한이 없습니다", HttpStatus.FORBIDDEN, "/");
        }
        userService.delete(id, principal.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "유저 삭제 성공", null), HttpStatus.OK);
    }

    @DeleteMapping("/admin/board/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable("id") int id) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("로그인이 필요합니다", HttpStatus.UNAUTHORIZED, "/admin/loginForm");
        }
        if (!principal.getRole().equals("admin")) {
            throw new CustomException("권한이 없습니다", HttpStatus.FORBIDDEN, "/");
        }
        boardService.delete(id, principal.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "게시물 삭제 성공", null), HttpStatus.OK);
    }

    @DeleteMapping("/admin/reply/{id}")
    public ResponseEntity<?> deleteReply(@PathVariable("id") int id) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("로그인이 필요합니다", HttpStatus.UNAUTHORIZED, "/admin/loginForm");
        }
        if (!principal.getRole().equals("admin")) {
            throw new CustomException("권한이 없습니다", HttpStatus.FORBIDDEN, "/");
        }
        replyService.delete(id, principal.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "댓글 삭제 성공", null), HttpStatus.OK);
    }
}
