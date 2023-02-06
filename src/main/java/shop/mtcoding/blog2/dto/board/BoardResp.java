package shop.mtcoding.blog2.dto.board;

import lombok.Getter;
import lombok.Setter;

public class BoardResp {

    @Getter
    @Setter
    public static class BoardListRespDto {
        private Integer id;
        private String title;
        private String username;
    }
}
