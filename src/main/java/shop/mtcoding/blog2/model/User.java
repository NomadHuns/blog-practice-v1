package shop.mtcoding.blog2.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Integer id;
    private String role;
    private String username;
    private String password;
    private String email;
    private String profile; // 사진의 경로 (static/images/ 폴더에 사진 추가하기)
    private Timestamp createdAt;
}
