package shop.mtcoding.blog2.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog2.dto.ResponseDto;
import shop.mtcoding.blog2.ex.CustomApiException;
import shop.mtcoding.blog2.model.User;
import shop.mtcoding.blog2.service.LoveService;

@Controller
@RequiredArgsConstructor
public class LoveController {
    private final HttpSession session;
    private final LoveService loveService;

    @GetMapping("/board/{id}/love")
    public ResponseEntity<?> love(@PathVariable("id") int boardId) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("로그인이 필요합니다", HttpStatus.UNAUTHORIZED);
        }
        loveService.doLove(boardId, principal.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "좋아요 성공", null), HttpStatus.OK);
    }

    @DeleteMapping("/board/{id}/love")
    public ResponseEntity<?> deleteLove(@PathVariable("id") int boardId) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("로그인이 필요합니다", HttpStatus.UNAUTHORIZED);
        }
        loveService.deleteLove(boardId, principal.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "좋아요 취소 성공", null), HttpStatus.OK);
    }
}
