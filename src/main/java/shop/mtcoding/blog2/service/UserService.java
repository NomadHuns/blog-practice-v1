package shop.mtcoding.blog2.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog2.dto.UserReq.JoinReqDto;
import shop.mtcoding.blog2.dto.UserReq.LoginReqDto;
import shop.mtcoding.blog2.ex.CustomException;
import shop.mtcoding.blog2.model.User;
import shop.mtcoding.blog2.model.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void join(JoinReqDto joinReqDto) {
        User user = userRepository.findByUsername(joinReqDto.getUsername());
        if (user != null) {
            throw new CustomException("이미 존재하는 username입니다.");
        }
        int result = userRepository.insert(joinReqDto.getUsername(), joinReqDto.getPassword(), joinReqDto.getEmail());
        if (result != 1) {
            throw new CustomException("회원가입 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public User login(LoginReqDto loginReqDto) {
        User user = new User();
        user.setUsername("cos");
        return user;
    }
}
