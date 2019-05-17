package com.dawid.overtime.employee.controller;

import com.dawid.overtime.entity.Employee;
import com.dawid.overtime.employee.service.EmployeeService;
import com.dawid.overtime.entity.Overtime;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee")
    public List<Employee> getAllEmployeesForApplicationUser() {
        return employeeService.findAllEmployeesByApplicationUserUsername();
    }

    @PostMapping("/employee")
    public Long addNewEmployee(@Valid @RequestBody Employee employee) {
        return employeeService.addNewEmployee(employee.getName(), employee.getLastName());
    }

    @DeleteMapping("/employee/{id}")
    public void deleteEmployee(@PathVariable String id) {
        employeeService.delete(id);
    }

    @PostMapping("/employee/overtime/{employeeId}")
    public void addOvertimeToEmployee(@PathVariable String employeeId, @RequestBody Overtime overtime) {
        employeeService.addOvertime(Long.parseLong(employeeId), overtime);
    }
}
