package com.dawid.overtime.employee.service;

import com.dawid.overtime.employee.repository.EmployeeRepository;
import com.dawid.overtime.employee.wrapper.ApplicationUserWrapper;
import com.dawid.overtime.utility.MyAssertion;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @Mock
    private ApplicationUserWrapper applicationUserWrapper;

    @Mock
    private EmployeeRepository employeeRepository;

    private EmployeeService instance;

    @Before
    public void setup() {
        instance = new EmployeeServiceImpl(employeeRepository, applicationUserWrapper);
    }

    @Test
    public void addNewEmployeeIsThrowingExceptionWhenApplicationUserNotFound() {
        Mockito.when(applicationUserWrapper.findByUsername("test")).thenThrow(new UsernameNotFoundException("test"));

        MyAssertion.assertDoesThrow(() -> instance.addNewEmployee("test", "test", "test"));
    }

}
