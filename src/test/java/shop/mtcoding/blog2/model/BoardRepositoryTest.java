package shop.mtcoding.blog2.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blog2.dto.board.BoardResp.BoardListRespDto;

@MybatisTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void findAllWithUser_test() throws Exception {
        // given
        ObjectMapper om = new ObjectMapper();

        // when
        List<BoardListRespDto> boardListRespDtos = boardRepository.findAllWithUser();
        System.out.println("디버그 : size() : " + boardListRespDtos.size());
        String responseBody = om.writeValueAsString(boardListRespDtos);
        System.out.println("디버그 : " + responseBody);
        // verify
        assertThat(boardListRespDtos.get(3).getUsername()).isEqualTo("ssar");
    }
}
