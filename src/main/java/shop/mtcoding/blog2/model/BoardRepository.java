package shop.mtcoding.blog2.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import shop.mtcoding.blog2.dto.board.BoardResp.BoardDetailRespDto;
import shop.mtcoding.blog2.dto.board.BoardResp.BoardListRespDto;

@Mapper
public interface BoardRepository {
    public List<Board> findAll();

    public Board findById(int id);

    public int insert(@Param("title") String title, @Param("content") String content, @Param("userId") int userId);

    public int deleteById(int id);

    public int updateById(@Param("id") int id, @Param("title") String title, @Param("content") String content,
            @Param("userId") int userId);

    public List<BoardListRespDto> findAllWithUser();

    public BoardDetailRespDto findByIdWithUser(int id);
}
