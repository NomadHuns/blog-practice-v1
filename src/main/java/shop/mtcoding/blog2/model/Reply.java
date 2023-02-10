package shop.mtcoding.blog2.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Reply {
    private Integer id;
    private String comment;
    private Integer userId;
    private Integer boardId;
    private Timestamp createdAt;
}
