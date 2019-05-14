package com.dawid.overtime.employee.service;

import com.dawid.overtime.entity.Employee;
import com.dawid.overtime.employee.exception.EmployeeIdNotFoundException;
import com.dawid.overtime.employee.exception.UnathorizedDeleteAttemptException;
import com.dawid.overtime.employee.repository.EmployeeRepository;
import com.dawid.overtime.employee.wrapper.ApplicationUserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private ApplicationUserWrapper applicationUserWrapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ApplicationUserWrapper applicationUserWrapper) {
        this.employeeRepository = employeeRepository;
        this.applicationUserWrapper = applicationUserWrapper;
    }


    @Override
    public Long addNewEmployee(String name, String lastName, String applicationUserUsername) {

        Employee employee = new Employee();
        employee.setName(name);
        employee.setLastName(lastName);
        employee.setApplicationUser(applicationUserWrapper.findByUsername(applicationUserUsername)
                .orElseThrow(() -> new UsernameNotFoundException(applicationUserUsername)));
        return employeeRepository.save(employee).getId();
    }

    @Override
    public List<Employee> findAllEmployeesByApplicationUserUsername(String username) {
        return employeeRepository.findAllByApplicationUser(applicationUserWrapper.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username)));
    }

    @Override
    public void delete(String id, String ownerUsername) {
        Long parsedId = Long.parseLong(id);
        Optional<Employee> optionalEmployee = employeeRepository.findById(parsedId);
        Employee employee = optionalEmployee.orElseThrow(() -> new EmployeeIdNotFoundException
                ("Employee with id " + id + " was not found"));
        if (employee.getApplicationUserUsername().equals(ownerUsername)){
            employeeRepository.delete(employee);
        }else {
            throw new UnathorizedDeleteAttemptException
                    ("Provided employee id doesn't belong to user with username " + ownerUsername);
        }
    }
}
