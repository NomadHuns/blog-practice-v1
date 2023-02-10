package shop.mtcoding.blog2.dto.reply;

import lombok.Getter;
import lombok.Setter;

public class ReplyReq {

    @Getter
    @Setter
    public static class ReplySaveReqDto {
        private String comment;
        private Integer boardId;
    }
}
