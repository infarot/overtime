package com.dawid.overtime.employee.service;

import com.dawid.overtime.employee.wrapper.AuthorizationHolder;
import com.dawid.overtime.entity.Employee;
import com.dawid.overtime.employee.repository.EmployeeRepository;
import com.dawid.overtime.employee.wrapper.ApplicationUserWrapper;
import com.dawid.overtime.entity.ApplicationUser;
import com.dawid.overtime.utility.MyAssertion;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @Mock
    private ApplicationUserWrapper applicationUserWrapper;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private AuthorizationHolder authorizationHolder;

    private EmployeeService instance;

    @Before
    public void setup() {
        instance = new EmployeeServiceImpl(employeeRepository, applicationUserWrapper, authorizationHolder);
    }

    @Test
    public void addNewEmployeeIsThrowingExceptionWhenApplicationUserNotFound() {
        MyAssertion.assertDoesThrow(() -> instance.addNewEmployee("test", "test"));
    }

    @Test
    public void findEmployeeByUsernameDoesThrowExceptionWhenApplicationUserNotFound() {
        MyAssertion.assertDoesThrow(() -> instance.findAllEmployeesByApplicationUserUsername());
    }

    @Test
    public void isUnableToDeleteNotOwnEmployee() {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername("testUser");
        applicationUser.setPassword("test1234");

        Employee employee = new Employee();
        employee.setName("testName");
        employee.setLastName("testLastName");
        employee.setId(1L);
        employee.setApplicationUser(applicationUser);

        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        MyAssertion.assertDoesThrow(() -> instance.delete("1"));
    }

    @Test
    public void isAbleToDeleteOwnEmployee() {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername("testUser");
        applicationUser.setPassword("test1234");

        Employee employee = new Employee();
        employee.setName("testName");
        employee.setLastName("testLastName");
        employee.setId(1L);
        employee.setApplicationUser(applicationUser);

        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        Mockito.when(authorizationHolder.loadCurrentUserUsername()).thenReturn("testUser");

        MyAssertion.assertDoesNotThrow(() -> instance.delete("1"));
    }

}
