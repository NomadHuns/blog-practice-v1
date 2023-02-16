package shop.mtcoding.blog2.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

@MybatisTest
public class AdminRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void findByTitleOrContent_test() throws Exception {
        // given
        ObjectMapper om = new ObjectMapper();
        String searchString = "열";

        // when
        List<Board> boardListRespDtos = boardRepository.findByTitleOrContent(searchString);
        System.out.println("테스트 : size() : " + boardListRespDtos.size());
        String responseBody = om.writeValueAsString(boardListRespDtos);
        System.out.println("테스트 : " + responseBody);
        // verify
        assertThat(boardListRespDtos.get(0).getTitle()).isEqualTo("열번째 글입니다.");
    }
}