package shop.mtcoding.blog2.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import shop.mtcoding.blog2.model.User;

/*
 * SpringBottTest는 통합테스트 (실제 환경과 동일하게 Bean이 생성됨)
 * (webEnvironment = WebEnvironment.MOCK) 가짜 환경에 IOC컨테이너가 있음
 * @AutoConfigureMockMvc는 Mock환경에 Ioc 컨테이너에 MockMvc Bean이 생성됨.
 * resultAction는 Response, Request 정보들이 모두 들어있다.
 */

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void join_test() throws Exception {
        // given
        String requestBody = "username=love&password=1234&email=alss11@nate.com";

        // when
        ResultActions resultActions = mvc.perform(post("/join").content(requestBody)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

        // then
        resultActions.andExpect(status().is3xxRedirection());
    }

    @Test
    public void login_test() throws Exception {
        // given
        String requestBody = "username=ssar&password=1234";

        // when
        ResultActions resultActions = mvc.perform(post("/login").content(requestBody)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        HttpSession session = resultActions.andReturn().getRequest().getSession();
        User principal = (User) session.getAttribute("principal");
        // System.out.println("디버그 : " + principal.getUsername());

        // then
        assertThat(principal.getUsername()).isEqualTo("ssar");
        resultActions.andExpect(status().is3xxRedirection());
    }
}