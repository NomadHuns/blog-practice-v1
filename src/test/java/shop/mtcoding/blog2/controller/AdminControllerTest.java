package shop.mtcoding.blog2.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blog2.dto.reply.ReplyResp.ReplyDetailAdminRespDto;
import shop.mtcoding.blog2.model.Board;
import shop.mtcoding.blog2.model.User;

/*
 * SpringBottTest는 통합테스트 (실제 환경과 동일하게 Bean이 생성됨)
 * (webEnvironment = WebEnvironment.MOCK) 가짜 환경에 IOC컨테이너가 있음
 * @AutoConfigureMockMvc는 Mock환경에 Ioc 컨테이너에 MockMvc Bean이 생성됨.
 * resultAction는 Response, Request 정보들이 모두 들어있다.
 */

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class AdminControllerTest {

    @Autowired
    private MockMvc mvc;

    private MockHttpSession mockSession;
    private ObjectMapper om;

    @BeforeEach
    public void setUp() {
        User user = new User();
        user.setId(1);
        user.setRole("admin");
        user.setUsername("alss");
        user.setPassword("1234");
        user.setEmail("alss@nate.com");
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        mockSession = new MockHttpSession();
        mockSession.setAttribute("principal", user);
    }

    @Test
    public void login_test() throws Exception {
        // given
        String requestBody = "username=alss&password=1234";

        // when
        ResultActions resultActions = mvc.perform(post("/admin/login").content(requestBody)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        HttpSession session = resultActions.andReturn().getRequest().getSession();
        User principal = (User) session.getAttribute("principal");

        // then
        assertThat(principal.getUsername()).isEqualTo("alss");
        resultActions.andExpect(status().is3xxRedirection());
    }

    @Test
    public void user_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(get("/admin").session(mockSession));
        Map<String, Object> map = resultActions.andReturn().getModelAndView().getModel();
        List<User> dtos = (List<User>) map.get("userList");
        om = new ObjectMapper();
        String model = om.writeValueAsString(dtos);
        System.out.println("테스트 : " + model);

        // then
        resultActions.andExpect(status().isOk());
        assertThat(dtos.size()).isEqualTo(2);
        assertThat(dtos.get(0).getUsername()).isEqualTo("ssar");
    }

    @Test
    public void searchUser_test() throws Exception {
        // given
        String searchString = "ssa";

        // when
        ResultActions resultActions = mvc
                .perform(post("/admin/user/search")
                        .content(searchString)
                        .contentType(MediaType.TEXT_PLAIN_VALUE).session(mockSession));
        String script = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + script);

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.code").value(1));
        resultActions.andExpect(jsonPath("$.data[0].id").value(1));
    }

    @Test
    public void board_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(get("/admin/board").session(mockSession));
        Map<String, Object> map = resultActions.andReturn().getModelAndView().getModel();
        List<Board> dtos = (List<Board>) map.get("boardList");
        om = new ObjectMapper();
        String model = om.writeValueAsString(dtos);
        System.out.println("테스트 : " + model);

        // then
        resultActions.andExpect(status().isOk());
        assertThat(dtos.size()).isEqualTo(10);
        assertThat(dtos.get(0).getTitle()).isEqualTo("첫번째 글입니다.");
    }

    @Test
    public void searchBoard_test() throws Exception {
        // given
        String searchString = "열";

        // when
        ResultActions resultActions = mvc
                .perform(post("/admin/board/search")
                        .content(searchString)
                        .contentType(MediaType.TEXT_PLAIN_VALUE).session(mockSession));
        String script = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + script);

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.code").value(1));
        resultActions.andExpect(jsonPath("$.data[0].id").value(10));
    }

    @Test
    public void searchReply_test() throws Exception {
        // given
        String searchString = "ssa";

        // when
        ResultActions resultActions = mvc
                .perform(post("/admin/reply/search")
                        .content(searchString)
                        .contentType(MediaType.TEXT_PLAIN_VALUE).session(mockSession));
        String script = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + script);

        // then
        resultActions.andExpect(status().isOk());
        // resultActions.andExpect(jsonPath("$.code").value(1));
        // resultActions.andExpect(jsonPath("$.data[0].comment").value("1번째 댓글입니다."));
    }

    @Test
    public void reply_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(get("/admin/reply").session(mockSession));
        Map<String, Object> map = resultActions.andReturn().getModelAndView().getModel();
        List<ReplyDetailAdminRespDto> dtos = (List<ReplyDetailAdminRespDto>) map.get("replyList");
        om = new ObjectMapper();
        String model = om.writeValueAsString(dtos);
        System.out.println("테스트 : " + model);

        // then
        resultActions.andExpect(status().isOk());
        assertThat(dtos.size()).isEqualTo(4);
        assertThat(dtos.get(0).getComment()).isEqualTo("1번째 댓글입니다.");
    }

    @Test
    public void deleteBoard_test() throws Exception {
        // given
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(delete("/admin/board/" + id).session(mockSession));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("디버그 : " + responseBody);

        // then
        resultActions.andExpect(jsonPath("$.code").value(1));
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void deleteUser_test() throws Exception {
        // given
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(delete("/admin/user/" + id).session(mockSession));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("디버그 : " + responseBody);

        // then
        resultActions.andExpect(jsonPath("$.code").value(1));
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void deleteReply_test() throws Exception {
        // given
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(delete("/admin/reply/" + id).session(mockSession));
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
}