package com.dawid.overtime.employee.service;

import com.dawid.overtime.entity.Employee;
import com.dawid.overtime.entity.Overtime;

import java.time.Duration;
import java.util.List;

public interface EmployeeService {

    Long addNewEmployee(String name, String lastName);
    List<Employee> findAllEmployeesByApplicationUserUsername();
    void delete(String id);
    void addOvertime(Long employeeId, Overtime overtime);
}
