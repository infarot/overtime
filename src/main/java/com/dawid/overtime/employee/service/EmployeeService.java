package com.dawid.overtime.employee.service;

import com.dawid.overtime.entity.Employee;
import com.dawid.overtime.entity.Overtime;
import com.dawid.overtime.entity.Shortage;

import java.time.Duration;
import java.util.List;

public interface EmployeeService {

    Long addNewEmployee(String name, String lastName);
    List<Employee> findAllEmployeesByApplicationUserUsername();
    void delete(String id);
    void addOvertime(Long employeeId, Overtime overtime);
    void addShortage(Long employeeId, Shortage shortage);
}
