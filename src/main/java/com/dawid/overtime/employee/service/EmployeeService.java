package com.dawid.overtime.employee.service;

import com.dawid.overtime.entity.Employee;

import java.util.List;

public interface EmployeeService {

    Long addNewEmployee(String name, String lastName, String applicationUserUsername);
    List<Employee> findAllEmployeesByApplicationUserUsername(String username);
    void delete(String id, String ownerUsername);
}
