package shop.mtcoding.blog2.dto.reply;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

public class ReplyResp {
    
    @Getter
    @Setter
    public static class ReplyDetailRespDto {
        private Integer id;
        private String comment;
        private Integer userId;
        private Integer boardId;
        private String username;
    }

    @Getter
    @Setter
    public static class ReplyDetailAdminRespDto {
        private Integer id;
        private String comment;
        private Integer userId;
        private Integer boardId;
        private String username;
        private Timestamp createdAt;
    }
}
