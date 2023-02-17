package shop.mtcoding.blog2.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Love {
    private Integer id;
    private Integer userId;
    private Integer boardId;
    private Timestamp createdAt;

    public Love(int id){
        this.id = id;
    }

    public Love(int boardId, int userId) {
        this.boardId = boardId;
        this.userId = userId;
    }
}
