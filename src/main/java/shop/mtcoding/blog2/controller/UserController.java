package shop.mtcoding.blog2.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog2.dto.user.UserReq.JoinReqDto;
import shop.mtcoding.blog2.dto.user.UserReq.LoginReqDto;
import shop.mtcoding.blog2.ex.CustomException;
import shop.mtcoding.blog2.model.User;
import shop.mtcoding.blog2.service.UserService;
import shop.mtcoding.blog2.util.JsoupUtil;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final HttpSession session;

    @GetMapping("/joinForm")
    public String joinForm(Model model) throws IOException {
        JsoupUtil.stockMarket(model);
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm(Model model) throws IOException {
        JsoupUtil.stockMarket(model);
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm(Model model) throws IOException {
        JsoupUtil.stockMarket(model);
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
        userService.join(joinReqDto);
        return "redirect:/loginForm";
    }

    @PostMapping("/login")
    public String login(LoginReqDto loginReqDto) {
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
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/user/profileUpdateForm")
    public String profileUpdateForm(Model model) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            return "redirect:/loginForm";
        }
        User userPS = userService.findById(principal.getId());
        model.addAttribute("user", userPS);
        return "user/profileUpdateForm";
    }

    @PostMapping("/user/profileUpdate")
    public String profileUpdate(MultipartFile profile) throws IOException {
        System.out.println("테스트 : " + profile.getContentType());
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            return "redirect:/loginForm";
        }
        if (profile.isEmpty()) {
            throw new CustomException("사진이 전송되지 않았습니다");
        }

        // 사진이 아니면 ex 터트리기

        // 2. 저장된 파일의 경로를 DB에 저장
        User userPS = userService.updateProfile(principal.getId(), profile);

        // 3. 수정된 데이터를 세션에 저장
        session.setAttribute("principal", userPS);
        return "redirect:/";
    }
}
