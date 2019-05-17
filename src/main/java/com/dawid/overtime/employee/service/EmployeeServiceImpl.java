package com.dawid.overtime.employee.service;

import com.dawid.overtime.employee.wrapper.AuthorizationHolder;
import com.dawid.overtime.entity.CustomHourStatistic;
import com.dawid.overtime.entity.Employee;
import com.dawid.overtime.employee.exception.EmployeeIdNotFoundException;
import com.dawid.overtime.employee.exception.UnathorizedDeleteAttemptException;
import com.dawid.overtime.employee.repository.EmployeeRepository;
import com.dawid.overtime.employee.wrapper.ApplicationUserWrapper;
import com.dawid.overtime.entity.Overtime;
import com.dawid.overtime.entity.Shortage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private ApplicationUserWrapper applicationUserWrapper;
    private AuthorizationHolder authorizationHolder;


    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               ApplicationUserWrapper applicationUserWrapper,
                               AuthorizationHolder authorizationHolder) {
        this.employeeRepository = employeeRepository;
        this.applicationUserWrapper = applicationUserWrapper;
        this.authorizationHolder = authorizationHolder;
    }


    @Override
    public Long addNewEmployee(String name, String lastName) {
        String applicationUserUsername = authorizationHolder.loadCurrentUserUsername();
        Employee employee = new Employee();
        employee.setName(name);
        employee.setLastName(lastName);
        employee.setApplicationUser(applicationUserWrapper.findByUsername(applicationUserUsername)
                .orElseThrow(() -> new UsernameNotFoundException(applicationUserUsername)));
        return employeeRepository.save(employee).getId();
    }

    @Override
    public List<Employee> findAllEmployeesByApplicationUserUsername() {
        String applicationUserUsername = authorizationHolder.loadCurrentUserUsername();
        List<Employee> employees = employeeRepository.findAllByApplicationUser(applicationUserWrapper.findByUsername(applicationUserUsername)
                .orElseThrow(() -> new UsernameNotFoundException(applicationUserUsername)));
        return calculateEmployeeHourBalance(employees);
    }

    private List<Employee> calculateEmployeeHourBalance(List<Employee> employees) {
        return employees;
    }

    @Override
    public void delete(String id) {
        Long parsedId = Long.parseLong(id);
        Employee employee = findById(parsedId);

        checkIfIsAuthorizedToAccessEmployee(employee);

        employeeRepository.delete(employee);
    }

    @Override
    public void addOvertime(Long id, Overtime overtime) {
        Employee employee = findById(id);
        checkIfIsAuthorizedToAccessEmployee(employee);

        CustomHourStatistic statistic = employee.initializeStats();
        Set<Overtime> overtimeSet = statistic.initializeOvertime();

        statistic.setEmployee(employee);

        overtime.setCustomHourStatistic(statistic);
        overtimeSet.add(overtime);

        statistic.setOvertime(overtimeSet);

        employee.setStatistic(statistic);

        employeeRepository.save(employee);
    }

    @Override
    public void addShortage(Long id, Shortage shortage) {
        Employee employee = findById(id);
        checkIfIsAuthorizedToAccessEmployee(employee);

        CustomHourStatistic statistic = employee.initializeStats();
        Set<Shortage> shortageSet = statistic.initializeShortage();

        statistic.setEmployee(employee);

        shortage.setCustomHourStatistic(statistic);
        shortageSet.add(shortage);

        statistic.setShortage(shortageSet);

        employee.setStatistic(statistic);

        employeeRepository.save(employee);
    }

    private Employee findById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeIdNotFoundException
                ("Employee with id " + id + " was not found"));
    }

    private void checkIfIsAuthorizedToAccessEmployee(Employee employee) {
        String applicationUserUsername = authorizationHolder.loadCurrentUserUsername();
        if (!employee.getApplicationUserUsername().equals(applicationUserUsername)) {
            throw new UnathorizedDeleteAttemptException
                    ("Provided employee id doesn't belong to user with username " + applicationUserUsername);
        }
    }


}
