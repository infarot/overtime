package com.dawid.overtime.employee.controller;

import com.dawid.overtime.entity.Employee;
import com.dawid.overtime.entity.ApplicationUser;

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

import javax.transaction.Transactional;

import java.time.Duration;

import static com.dawid.overtime.utility.JsonParser.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;

    private String token;

    private Employee employee;

    @Before
    public void setup() throws Exception {
        ApplicationUser user = new ApplicationUser();
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

    @Test
    public void isAbleToDeleteExistingEmployee() throws Exception {

        MvcResult result = mvc.perform(post("/employee")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(employee)))
                .andExpect(status().isOk()).andReturn();
        long id = Long.parseLong(result.getResponse().getContentAsString());

        mvc.perform(delete("/employee/" + id)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void isUnableToDeleteNotExistingEmployee() throws Exception {

        mvc.perform(delete("/employee/1")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void isAbleToAddOvertimeToEmployee() throws Exception {

        MvcResult result = mvc.perform(post("/employee")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(employee)))
                .andExpect(status().isOk()).andReturn();
        long id = Long.parseLong(result.getResponse().getContentAsString());


        mvc.perform(post("/employee/" + id + "/2/35")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void isAbleToGetOvertimeOfEmployee() throws Exception {

        MvcResult result = mvc.perform(post("/employee")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(employee)))
                .andExpect(status().isOk()).andReturn();
        long id = Long.parseLong(result.getResponse().getContentAsString());


        mvc.perform(post("/employee/" + id + "/2/35")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/employee")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].stats.overtime", Matchers.is(Duration.ofHours(2).plusMinutes(35).toString())));
    }


}
