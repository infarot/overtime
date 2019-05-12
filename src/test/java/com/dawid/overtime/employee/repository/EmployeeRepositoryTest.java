package com.dawid.overtime.employee.repository;

import com.dawid.overtime.employee.entity.Employee;
import com.dawid.overtime.utility.MyAssertion;
import com.dawid.overtime.security.entity.ApplicationUser;
import com.dawid.overtime.security.repository.ApplicationUserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;


    @Test
    public void isAbleToSaveEmployeeWithCorrectCredentials() {

        Employee employee = new Employee();
        employee.setName("name");
        employee.setLastName("lastName");
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername("test");
        applicationUser.setPassword("test1234");
        employee.setApplicationUser(applicationUser);

        applicationUserRepository.save(applicationUser);

        MyAssertion.assertDoesNotThrow(() -> employeeRepository.save(employee));

    }

    @Test
    public void isUnableToSaveEmployeeWithBadCredentials() {

        Employee employee = new Employee();
        employee.setName("name1");
        employee.setLastName("lastName");
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername("test");
        applicationUser.setPassword("test1234");
        employee.setApplicationUser(applicationUser);

        applicationUserRepository.save(applicationUser);

        MyAssertion.assertDoesThrow(() -> employeeRepository.save(employee));


        Employee employee1 = new Employee();
        employee1.setName("name");
        employee1.setLastName("lastName2");
        employee1.setApplicationUser(applicationUser);

        applicationUserRepository.save(applicationUser);

        MyAssertion.assertDoesThrow(() -> employeeRepository.save(employee1));

        Employee employee2 = new Employee();
        employee2.setName("");
        employee2.setLastName("");
        employee2.setApplicationUser(applicationUser);

        applicationUserRepository.save(applicationUser);

        MyAssertion.assertDoesThrow(() -> employeeRepository.save(employee2));

    }

    @Test
    public void isAbleToFindEmployeesListForDefinedUser() {

        Employee employee = new Employee();
        employee.setName("name");
        employee.setLastName("lastName");
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername("test");
        applicationUser.setPassword("test1234");
        employee.setApplicationUser(applicationUser);

        applicationUserRepository.save(applicationUser);
        employeeRepository.save(employee);

        Assert.assertEquals(employeeRepository.findAllByApplicationUser(applicationUser), Collections.singletonList(employee));
    }
}
