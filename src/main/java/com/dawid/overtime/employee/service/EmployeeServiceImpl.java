package com.dawid.overtime.employee.service;

import com.dawid.overtime.employee.entity.Employee;
import com.dawid.overtime.employee.repository.EmployeeRepository;
import com.dawid.overtime.security.entity.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InvalidAttributeValueException;
import javax.validation.Valid;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }


    @Override
    public void addNewEmployee(String name, String lastName, ApplicationUser applicationUser) {

        Employee employee = new Employee();
        employee.setName(name);
        employee.setLastName(lastName);
        employee.setApplicationUser(applicationUser);

        employeeRepository.save(employee);
    }
}
