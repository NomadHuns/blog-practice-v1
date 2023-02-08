package shop.mtcoding.blog2.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blog2.dto.board.BoardReq.BoardUpdateReqDto;
import shop.mtcoding.blog2.dto.board.BoardResp.BoardDetailRespDto;
import shop.mtcoding.blog2.dto.board.BoardResp.BoardListRespDto;
import shop.mtcoding.blog2.model.User;

@Transactional // 메서드 실행 직후 롤백, auto_increment 초기화 되지 않음.
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class BoardControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    private MockHttpSession mockSession;

    @BeforeEach
    public void setUp() {
        User user = new User();
        user.setId(1);
        user.setUsername("ssar");
        user.setPassword("1234");
        user.setEmail("ssar@nate.com");
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        mockSession = new MockHttpSession();
        mockSession.setAttribute("principal", user);
    }

    @Test
    public void save_test() throws Exception {
        // given
        String title = "";
        for (int i = 0; i < 99; i++) {
            title += "가";
        }

        String requestBody = "title=" + title + "&content=내용1";

        // when
        ResultActions resultActions = mvc.perform(
                post("/board")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .session(mockSession));

        // then
        resultActions.andExpect(status().is3xxRedirection());
    }

    @Test
    public void main_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(get("/"));
        Map<String, Object> map = resultActions.andReturn().getModelAndView().getModel();
        List<BoardListRespDto> dtos = (List<BoardListRespDto>) map.get("boardList");
        String model = om.writeValueAsString(dtos);
        System.out.println("디버그 : " + model);

        // then
        resultActions.andExpect(status().isOk());
        assertThat(dtos.size()).isEqualTo(10);
        assertThat(dtos.get(3).getUsername()).isEqualTo("ssar");
        assertThat(dtos.get(7).getUsername()).isEqualTo("cos");
    }

    @Test
    public void detail_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(get("/board/1"));
        Map<String, Object> map = resultActions.andReturn().getModelAndView().getModel();
        BoardDetailRespDto dto = (BoardDetailRespDto) map.get("board");
        String model = om.writeValueAsString(dto);
        System.out.println("디버그 : " + model);

        // then
        resultActions.andExpect(status().isOk());
        assertThat(dto.getUsername()).isEqualTo("ssar");
        assertThat(dto.getTitle()).isEqualTo("첫번째 글입니다.");
        assertThat(dto.getContent()).isEqualTo("첫번째 내용입니다");
        assertThat(dto.getId()).isEqualTo(1);
        assertThat(dto.getUserId()).isEqualTo(1);
    }

    @Test
    public void delete_test() throws Exception {
        // given
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(delete("/board/" + id).session(mockSession));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("디버그 : " + responseBody);

        /*
         * jsonPath
         * 최상위 : $
         * 객체탐색 : .
         * 배열 : [0]
         */
        // then
        resultActions.andExpect(jsonPath("$.code").value(1));
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void update_test() throws Exception {
        // given
        int id = 1;
        BoardUpdateReqDto mockBoardUpdateReqDto = new BoardUpdateReqDto();
        mockBoardUpdateReqDto.setTitle("제목입니다.");
        mockBoardUpdateReqDto.setContent("내용입니다.");
        String requestBody = om.writeValueAsString(mockBoardUpdateReqDto);

        // when
        ResultActions resultActions = mvc.perform(
                put("/board/" + id)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .session(mockSession));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("디버그 : " + responseBody);

        // then
        resultActions.andExpect(jsonPath("$.code").value(1));
        resultActions.andExpect(status().isOk());
    }
}
