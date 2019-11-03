package com.dawid.overtime.employee.controller;

import com.dawid.overtime.entity.ApplicationUserEntity;

import com.dawid.overtime.entity.EmployeeEntity;
import com.dawid.overtime.entity.OvertimeEntity;
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
import java.time.LocalDate;

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

    private EmployeeEntity employee;

    @Before
    public void setup() throws Exception {
        ApplicationUserEntity user = new ApplicationUserEntity();
        user.setUsername("test");
        user.setPassword("test1234");
        user.setEmail("test@gmail.com");

        employee = new EmployeeEntity();
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

        token = String.valueOf(result.getResponse().getHeaderValue("Authorization"));
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

        OvertimeEntity overtime = new OvertimeEntity();
        overtime.setAmount(Duration.ofHours(2).plusMinutes(35));
        overtime.setOvertimeDate(LocalDate.of(2019, 5, 15));

        mvc.perform(post("/employee/overtime/" + id)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(overtime)))
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

        OvertimeEntity overtime = new OvertimeEntity();
        overtime.setAmount(Duration.ofHours(2).plusMinutes(35));
        overtime.setOvertimeDate(LocalDate.of(2019, 5, 15));

        mvc.perform(post("/employee/overtime/" + id)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(overtime)))
                .andExpect(status().isOk());

        mvc.perform(get("/employee")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].overtime.[0].amount",
                        Matchers.is(Duration.ofHours(2).plusMinutes(35).toString())));
    }


    @Test
    public void isAbleToGetCurrentHourBalanceOfEmployee() throws Exception {

        MvcResult result = mvc.perform(post("/employee")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(employee)))
                .andExpect(status().isOk()).andReturn();
        long id = Long.parseLong(result.getResponse().getContentAsString());

        OvertimeEntity overtime = new OvertimeEntity();
        overtime.setId(1L);
        overtime.setAmount(Duration.ofHours(2).plusMinutes(35));
        overtime.setOvertimeDate(LocalDate.of(2019, 5, 15));

        mvc.perform(post("/employee/overtime/" + id)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(overtime)))
                .andExpect(status().isOk());


        mvc.perform(get("/employee")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].balance",
                        Matchers.is(Duration.ofHours(2).plusMinutes(35).toString())));
    }

    @Test
    public void isAbleToDeleteOvertimeFromOwnedEmployee() throws Exception {
        MvcResult result = mvc.perform(post("/employee")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(employee)))
                .andExpect(status().isOk()).andReturn();
        long employeeId = Long.parseLong(result.getResponse().getContentAsString());

        OvertimeEntity overtime = new OvertimeEntity();
        overtime.setId(1L);
        overtime.setAmount(Duration.ofHours(2).plusMinutes(35));
        overtime.setOvertimeDate(LocalDate.of(2019, 5, 15));

        mvc.perform(post("/employee/overtime/" + employeeId)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(overtime)))
                .andExpect(status().isOk());


        MvcResult result1 = mvc.perform(get("/employee")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        long overtimeId = Long.parseLong(String.valueOf(result1.getResponse().getContentAsString().charAt(79)));

        mvc.perform(delete("/employee/overtime/" + employeeId + "/" + overtimeId)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
