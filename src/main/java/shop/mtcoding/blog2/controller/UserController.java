package shop.mtcoding.blog2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog2.dto.UserReq.JoinReqDto;
import shop.mtcoding.blog2.ex.CustomException;
import shop.mtcoding.blog2.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

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
            throw new CustomException("username을 입력하세요.");
        }
        if (joinReqDto.getPassword().isEmpty() || joinReqDto.getPassword() == null) {
            throw new CustomException("password를 입력하세요.");
        }
        if (joinReqDto.getEmail().isEmpty() || joinReqDto.getEmail() == null) {
            throw new CustomException("email을 입력하세요.");
        }
        userService.join(joinReqDto);
        return "redirect:/loginForm";
    }
}
