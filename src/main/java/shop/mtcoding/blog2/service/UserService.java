package shop.mtcoding.blog2.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.blog2.dto.user.UserReq.JoinReqDto;
import shop.mtcoding.blog2.dto.user.UserReq.LoginReqDto;
import shop.mtcoding.blog2.ex.CustomException;
import shop.mtcoding.blog2.model.User;
import shop.mtcoding.blog2.model.UserRepository;
import shop.mtcoding.blog2.util.PathUtil;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void join(JoinReqDto joinReqDto) {
        User principal = userRepository.findByUsername(joinReqDto.getUsername());
        if (principal != null) {
            throw new CustomException("동일한 유저네임이 존재합니다.");
        }
        int result = userRepository.insert(joinReqDto.getUsername(), joinReqDto.getPassword(), joinReqDto.getEmail());
        if (result != 1) {
            throw new CustomException("회원가입 실패");
        }
    }

    @Transactional(readOnly = true)
    public User login(LoginReqDto loginReqDto) {
        User principal = userRepository.findByUsernameAndPassword(loginReqDto.getUsername(), loginReqDto.getPassword());
        if (principal == null) {
            throw new CustomException("유저네임이나 패스워드를 확인하세요.");
        }
        return principal;
    }

    @Transactional(readOnly = true)
    public User findById(int principalId) {
        User principal = userRepository.findById(principalId);
        return principal;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        List<User> userPSList = userRepository.findAll();
        return userPSList;
    }

    @Transactional
    public User updateProfile(int principalId, MultipartFile profile) {
        // 1. 파일은 하드디스크에 저장
        // 이미지 경로 : /static/images
        String uuidImageName = PathUtil.writeImageFile(profile);
        // 2. 저장된 파일의 경로를 DB에 저장
        User userPS = userRepository.findById(principalId);
        try {
            userRepository.updateById(userPS.getId(), userPS.getUsername(), userPS.getPassword(),
                    userPS.getEmail(), uuidImageName, userPS.getCreatedAt());
        } catch (Exception e) {
            throw new CustomException("프로필 변경 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return userPS;
    }
}
