package shop.mtcoding.blog2.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog2.dto.UserReq.JoinReqDto;
import shop.mtcoding.blog2.model.User;
import shop.mtcoding.blog2.model.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public int join(JoinReqDto joinReqDto) {
        User principal = userRepository.findByUsername(joinReqDto.getUsername());
        if (principal != null) {
            return -1;
        }
        int result = userRepository.insert(joinReqDto.getUsername(), joinReqDto.getPassword(), joinReqDto.getEmail());
        return result;
    }
}
