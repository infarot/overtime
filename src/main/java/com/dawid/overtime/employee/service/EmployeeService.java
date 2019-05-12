package com.dawid.overtime.employee.service;

import com.dawid.overtime.employee.entity.Employee;
import com.dawid.overtime.security.entity.ApplicationUser;

public interface EmployeeService {

    void addNewEmployee(String name, String lastName, ApplicationUser applicationUser);
}
