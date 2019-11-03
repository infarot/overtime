package com.dawid.overtime.employee.service;

import com.dawid.overtime.dto.EmployeeDto;
import com.dawid.overtime.dto.OvertimeDto;

import java.util.List;

public interface EmployeeService {

    Long addNewEmployee(String name, String lastName);

    List<EmployeeDto> findAllEmployeesByApplicationUserUsername();

    void delete(String id);

    void addOvertime(Long employeeId, OvertimeDto overtime);

    void deleteOvertime(Long employeeId, Long overtimeId);
}
