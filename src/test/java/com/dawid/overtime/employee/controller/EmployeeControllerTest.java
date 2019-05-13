package com.dawid.overtime.employee.controller;

import com.dawid.overtime.employee.entity.Employee;
import com.dawid.overtime.security.entity.ApplicationUser;
import com.dawid.overtime.utility.JsonParser;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.dawid.overtime.utility.JsonParser.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;

    private String token;

    private ApplicationUser user;

    private Employee employee;

    @Before
    public void setup() throws Exception {
        user = new ApplicationUser();
        user.setUsername("test");
        user.setPassword("test1234");

        employee = new Employee();
        employee.setName("test");
        employee.setLastName("test");

        mvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)));

        MvcResult result = mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        token = result.getResponse().getHeaderValue("Authorization").toString();
    }

    @Test
    public void isAbleToAddNewEmployee() throws Exception {
        mvc.perform(post("/employee")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(employee)))
                .andExpect(status().isOk());
    }

    @Test
    public void isAbleToGetAllEmployeesForApplicationUser() throws Exception {
        mvc.perform(post("/employee")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(employee)))
                .andExpect(status().isOk());

        mvc.perform(get("/employee")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name", Matchers.is("test")))
                .andExpect(jsonPath("$.[0].lastName", Matchers.is("test")));
    }
}
