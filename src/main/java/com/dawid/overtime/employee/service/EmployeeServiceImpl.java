package com.dawid.overtime.employee.service;

import com.dawid.overtime.employee.exception.OvertimeIdNotFoundException;
import com.dawid.overtime.employee.repository.OvertimeRepository;
import com.dawid.overtime.employee.wrapper.AuthorizationHolder;
import com.dawid.overtime.entity.CustomHourStatistic;
import com.dawid.overtime.entity.Employee;
import com.dawid.overtime.employee.exception.EmployeeIdNotFoundException;
import com.dawid.overtime.employee.exception.UnathorizedDeleteAttemptException;
import com.dawid.overtime.employee.repository.EmployeeRepository;
import com.dawid.overtime.employee.wrapper.ApplicationUserWrapper;
import com.dawid.overtime.entity.Overtime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private ApplicationUserWrapper applicationUserWrapper;
    private AuthorizationHolder authorizationHolder;
    private OvertimeRepository overtimeRepository;


    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               ApplicationUserWrapper applicationUserWrapper,
                               AuthorizationHolder authorizationHolder,
                               OvertimeRepository overtimeRepository) {
        this.employeeRepository = employeeRepository;
        this.applicationUserWrapper = applicationUserWrapper;
        this.authorizationHolder = authorizationHolder;
        this.overtimeRepository = overtimeRepository;
    }


    @Override
    public Long addNewEmployee(String name, String lastName) {
        String applicationUserUsername = authorizationHolder.loadCurrentUserUsername();
        Employee employee = new Employee();
        employee.setName(name);
        employee.setLastName(lastName);
        employee.setApplicationUser(applicationUserWrapper.findByUsername(applicationUserUsername)
                .orElseThrow(() -> new UsernameNotFoundException(applicationUserUsername)));
        CustomHourStatistic statistic = employee.initializeStats();
        statistic.setEmployee(employee);
        employee.setStatistic(statistic);
        return employeeRepository.save(employee).getId();
    }

    @Override
    public List<Employee> findAllEmployeesByApplicationUserUsername() {
        String applicationUserUsername = authorizationHolder.loadCurrentUserUsername();
        List<Employee> employees = employeeRepository
                .findAllByApplicationUser(applicationUserWrapper.findByUsername(applicationUserUsername)
                        .orElseThrow(() -> new UsernameNotFoundException(applicationUserUsername)));
        return calculateEmployeeHourBalance(employees);
    }

    private List<Employee> calculateEmployeeHourBalance(List<Employee> employees) {
        List<Employee> employeesWithBalance = new ArrayList<>();
        for (Employee e : employees) {
            employeesWithBalance.add(calculateEmployeeHourBalance(e));
        }
        return employeesWithBalance;
    }

    private Employee calculateEmployeeHourBalance(Employee employee) {
        CustomHourStatistic customHourStatistic = employee.initializeStats();
        Set<Overtime> overtimeSet = customHourStatistic.getOvertime();
        if (overtimeSet != null) {
            List<Duration> durationList = customHourStatistic.getOvertime()
                    .stream()
                    .filter((a -> a.getPickUpDate() == null))
                    .map(Overtime::getAmount)
                    .collect(Collectors.toList());

            Duration duration = Duration.ZERO;
            for (Duration d : durationList) {
                duration = duration.plus(d);
            }
            customHourStatistic.setBalance(duration);
            Employee employeeWithBalance = employee.clone();
            employeeWithBalance.setStatistic(customHourStatistic);
            return employeeWithBalance;
        }
        return employee;
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
        overtimeSet.remove(overtime);
        overtimeSet.add(overtime);

        statistic.setOvertime(overtimeSet);

        employee.setStatistic(statistic);

        employeeRepository.save(employee);
    }

    @Override
    public void deleteOvertime(Long employeeId, Long overtimeId) {
        Employee employee = findById(employeeId);
        checkIfIsAuthorizedToAccessEmployee(employee);
        overtimeRepository.delete(overtimeRepository.findById(overtimeId)
                .orElseThrow(OvertimeIdNotFoundException::new));

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
