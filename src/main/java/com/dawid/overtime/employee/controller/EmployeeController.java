package com.dawid.overtime.employee.controller;

import com.dawid.overtime.dto.EmployeeDto;
import com.dawid.overtime.dto.OvertimeDto;
import com.dawid.overtime.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @OvertimeContext
    @GetMapping("/employee")
    public List<EmployeeDto> getAllEmployeesForApplicationUser() {
        return employeeService.findAllEmployeesByApplicationUserUsername();
    }

    @OvertimeContext
    @PostMapping("/employee")
    public Long addNewEmployee(@Valid @RequestBody EmployeeDto employee) {
        return employeeService.addNewEmployee(employee.getName(), employee.getLastName());
    }

    @OvertimeContext
    @DeleteMapping("/employee/{id}")
    public void deleteEmployee(@PathVariable String id) {
        employeeService.delete(id);
    }

    @OvertimeContext
    @PostMapping("/employee/overtime/{employeeId}")
    public void addOvertimeToEmployee(@PathVariable String employeeId, @RequestBody OvertimeDto overtime) {
        employeeService.addOvertime(Long.parseLong(employeeId), overtime);
    }

    @OvertimeContext
    @DeleteMapping("employee/overtime/{employeeId}/{overtimeId}")
    public void deleteOvertimeFromEmployee(@PathVariable String employeeId, @PathVariable String overtimeId) {
        employeeService.deleteOvertime(Long.parseLong(employeeId), Long.parseLong(overtimeId));
    }
}
