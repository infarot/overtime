package com.dawid.overtime.employee.service;

import com.dawid.overtime.employee.entity.Employee;
import com.dawid.overtime.security.entity.ApplicationUser;

import java.util.List;

public interface EmployeeService {

    void addNewEmployee(String name, String lastName, String applicationUserUsername);
    List<Employee> findAllEmployeesByApplicationUsername(String username);
}
