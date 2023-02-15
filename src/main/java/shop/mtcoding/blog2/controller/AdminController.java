package shop.mtcoding.blog2.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog2.dto.user.UserReq.LoginReqDto;
import shop.mtcoding.blog2.ex.CustomException;
import shop.mtcoding.blog2.model.User;
import shop.mtcoding.blog2.service.UserService;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final HttpSession session;
    private final UserService userService;

    @GetMapping({ "/admin", "/admin/user" })
    public String user() {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("로그인이 필요합니다", HttpStatus.UNAUTHORIZED, "/admin/loginForm");
        }
        if (!principal.getRole().equals("admin")) {
            throw new CustomException("권한이 없습니다", HttpStatus.FORBIDDEN, "/");
        }
        return "admin/user";
    }

    @GetMapping("/admin/board")
    public String board() {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("로그인이 필요합니다", HttpStatus.UNAUTHORIZED, "/admin/loginForm");
        }
        if (!principal.getRole().equals("admin")) {
            throw new CustomException("권한이 없습니다", HttpStatus.FORBIDDEN, "/");
        }
        return "admin/board";
    }

    @GetMapping("/admin/reply")
    public String reply() {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("로그인이 필요합니다", HttpStatus.UNAUTHORIZED, "/admin/loginForm");
        }
        if (!principal.getRole().equals("admin")) {
            throw new CustomException("권한이 없습니다", HttpStatus.FORBIDDEN, "/");
        }
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
}
