package com.dawid.overtime.security.controller;

import com.dawid.overtime.security.entity.ApplicationUser;
import com.dawid.overtime.security.repository.ApplicationUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class AuthControllerTest {

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String properPassword = "test1234";
    private String properUsername = "test";


    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private MockMvc mvc;

    @After
    public void tearDown() {
        applicationUserRepository.deleteAll();
    }

    @Test
    public void isSignUpWithProperCredentialsWorking() throws Exception {
        ApplicationUser user = new ApplicationUser();
        user.setUsername(properUsername);
        user.setPassword(properPassword);

        mvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void isSignUpWithToShortUsernameNotWorking() throws Exception {
        String toShortUsername = "t";
        ApplicationUser user = new ApplicationUser();
        user.setUsername(toShortUsername);
        user.setPassword(properPassword);

        mvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void isSignUpWithToLongUsernameNotWorking() throws Exception {
        String toLongUsername = "123456789123456789123";
        ApplicationUser user = new ApplicationUser();
        user.setUsername(toLongUsername);
        user.setPassword(properPassword);

        mvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void isSignUpWithToLongPasswordNotWorking() throws Exception {
        String toLongPassword = "NiotXfybSOWfhj1QdMFuOKzOhBxrwWaBzKVUgQd0MONkaK4UFF6CkQat3zNxRPMtB7Dek1YRiZIlFp9MYr4hqSzhq92ASaqOFgQij";
        ApplicationUser user = new ApplicationUser();
        user.setUsername(properUsername);
        user.setPassword(toLongPassword);

        mvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void isSignUpWithToShortPasswordNotWorking() throws Exception {
        String toShortPassword = "t";
        ApplicationUser user = new ApplicationUser();
        user.setUsername(properUsername);
        user.setPassword(toShortPassword);

        mvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void isSignUpWithNotAvailableUsernameNotWorking() throws Exception {
        ApplicationUser user = new ApplicationUser();
        user.setUsername(properUsername);
        user.setPassword(properPassword);

        mvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", Matchers.is("Username is already taken!")));
    }

    @Test
    public void isLoginWithProperCredentialsWorking() throws Exception {
        ApplicationUser user = new ApplicationUser();
        user.setUsername(properUsername);
        user.setPassword(properPassword);

        mvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void isLoginWithIncorrectCredentialsNotWorking() throws Exception {
        String properUsername = "test";
        String toShortPassword = "test1234";
        ApplicationUser user = new ApplicationUser();
        user.setUsername(properUsername);
        user.setPassword(toShortPassword);

        mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void isForbiddenToSeeActuatorWhenNotAuthenticated() throws Exception {

        mvc.perform(get("/actuator")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void isPermittedToSeeActuatorWhenAuthenticated() throws Exception {

        ApplicationUser user = new ApplicationUser();
        user.setUsername(properUsername);
        user.setPassword(properPassword);

        mvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        MvcResult result = mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String token = result.getResponse().getHeaderValue("Authorization").toString();

        mvc.perform(get("/actuator")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
