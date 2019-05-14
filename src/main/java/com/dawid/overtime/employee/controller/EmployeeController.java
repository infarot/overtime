package com.dawid.overtime.employee.controller;

import com.dawid.overtime.entity.Employee;
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
        return employeeService.findAllEmployeesByApplicationUserUsername(loadCurrentUserUsername());
    }

    @PostMapping("/employee")
    public Long addNewEmployee(@Valid @RequestBody Employee employee) {
       return employeeService.addNewEmployee(employee.getName(), employee.getLastName(), loadCurrentUserUsername());
    }

    @DeleteMapping("/employee/{id}")
    public void deleteEmployee(@PathVariable String id){
        String currentUser = loadCurrentUserUsername();
        employeeService.delete(id, currentUser);
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
