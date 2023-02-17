package shop.mtcoding.blog2.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blog2.model.User;

/*
 * SpringBottTest는 통합테스트 (실제 환경과 동일하게 Bean이 생성됨)
 * (webEnvironment = WebEnvironment.MOCK) 가짜 환경에 IOC컨테이너가 있음
 * @AutoConfigureMockMvc는 Mock환경에 Ioc 컨테이너에 MockMvc Bean이 생성됨.
 * resultAction는 Response, Request 정보들이 모두 들어있다.
 */

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class LoveControllerTest {

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
    public void save_test() throws Exception {
        // given
        int boardId = 5;

        // when
        ResultActions resultActions = mvc.perform(
                get("/board/" + boardId + "/love")
                        .session(mockSession));

        // then
        resultActions.andExpect(status().isOk());
    }
}