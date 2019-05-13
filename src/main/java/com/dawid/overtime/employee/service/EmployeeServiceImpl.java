package com.dawid.overtime.employee.service;

import com.dawid.overtime.employee.entity.Employee;
import com.dawid.overtime.employee.repository.EmployeeRepository;
import com.dawid.overtime.employee.wrapper.ApplicationUserWrapper;
import com.dawid.overtime.security.entity.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.InvalidAttributeValueException;
import javax.validation.Valid;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private ApplicationUserWrapper applicationUserWrapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ApplicationUserWrapper applicationUserWrapper){
        this.employeeRepository = employeeRepository;
        this.applicationUserWrapper = applicationUserWrapper;
    }


    @Override
    public void addNewEmployee(String name, String lastName, String applicationUserUsername) {

        Employee employee = new Employee();
        employee.setName(name);
        employee.setLastName(lastName);
        employee.setApplicationUser(applicationUserWrapper.findByUsername(applicationUserUsername)
                .orElseThrow(()-> new UsernameNotFoundException(applicationUserUsername)));

        employeeRepository.save(employee);
    }
}
