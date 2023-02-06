package shop.mtcoding.blog2.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog2.dto.UserReq.JoinReqDto;
import shop.mtcoding.blog2.dto.UserReq.LoginReqDto;
import shop.mtcoding.blog2.ex.CustomException;
import shop.mtcoding.blog2.model.User;
import shop.mtcoding.blog2.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final HttpSession session;

    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    @PostMapping("/join")
    public String join(JoinReqDto joinReqDto) {
        if (joinReqDto.getUsername().isEmpty() || joinReqDto.getUsername() == null) {
            throw new CustomException("유저이름을 입력하세요.");
        }
        if (joinReqDto.getPassword().isEmpty() || joinReqDto.getPassword() == null) {
            throw new CustomException("패스워드를 입력하세요.");
        }
        if (joinReqDto.getEmail().isEmpty() || joinReqDto.getEmail() == null) {
            throw new CustomException("이메일을 입력하세요.");
        }
        int result = userService.join(joinReqDto);
        if (result != 1) {
            throw new CustomException("회원가입 실패");
        }
        return "redirect:/loginForm";
    }

    @PostMapping("/login")
    public String login(LoginReqDto loginReqDto){
        if (loginReqDto.getUsername().isEmpty() || loginReqDto.getUsername() == null) {
            throw new CustomException("유저이름을 입력하세요.");
        }
        if (loginReqDto.getPassword().isEmpty() || loginReqDto.getPassword() == null) {
            throw new CustomException("패스워드를 입력하세요.");
        }
        User principal = userService.login(loginReqDto);
        session.setAttribute("principal", principal);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(){
        session.invalidate();
        return "redirect:/";
    }
}
