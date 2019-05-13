package com.dawid.overtime.employee.controller;

import com.dawid.overtime.employee.entity.Employee;
import com.dawid.overtime.employee.service.EmployeeService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        return employeeService.findAllEmployeesByApplicationUsername(loadCurrentUserUsername());
    }

    @PostMapping("/employee")
    public void addNewEmployee(@Valid @RequestBody Employee employee) {
        employeeService.addNewEmployee(employee.getName(), employee.getLastName(), loadCurrentUserUsername());
    }

    private String loadCurrentUserUsername() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
