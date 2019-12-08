package com.dawid.overtime.employee.service;

import com.dawid.overtime.dto.EmployeeDto;
import com.dawid.overtime.dto.OvertimeDto;
import com.dawid.overtime.employee.exception.OvertimeIdNotFoundException;
import com.dawid.overtime.employee.repository.OvertimeRepository;
import com.dawid.overtime.employee.wrapper.AuthorizationHolder;
import com.dawid.overtime.entity.CustomHourStatisticEntity;
import com.dawid.overtime.employee.exception.EmployeeIdNotFoundException;
import com.dawid.overtime.employee.exception.UnathorizedDeleteAttemptException;
import com.dawid.overtime.employee.repository.EmployeeRepository;
import com.dawid.overtime.employee.wrapper.ApplicationUserWrapper;
import com.dawid.overtime.entity.EmployeeEntity;
import com.dawid.overtime.entity.OvertimeEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ApplicationUserWrapper applicationUserWrapper;
    private final AuthorizationHolder authorizationHolder;
    private final OvertimeRepository overtimeRepository;
    private final EmployeeMapper employeeMapper;
    private final OvertimeMapper overtimeMapper;

    @Override
    public Long addNewEmployee(String name, String lastName) {
        String applicationUserUsername = authorizationHolder.loadCurrentUserUsername();
        EmployeeEntity employee = new EmployeeEntity();
        employee.setName(name);
        employee.setLastName(lastName);
        employee.setApplicationUser(applicationUserWrapper.findByUsername(applicationUserUsername)
                .orElseThrow(() -> new UsernameNotFoundException(applicationUserUsername)));
        CustomHourStatisticEntity statistic = employee.initializeStats();
        statistic.setEmployee(employee);
        employee.setStatistic(statistic);
        return employeeRepository.save(employee).getId();
    }

    @Override
    public List<EmployeeDto> findAllEmployeesByApplicationUserUsername() {
        String applicationUserUsername = authorizationHolder.loadCurrentUserUsername();
        List<EmployeeEntity> employees = employeeRepository
                .findAllByApplicationUser(applicationUserWrapper.findByUsername(applicationUserUsername)
                        .orElseThrow(() -> new UsernameNotFoundException(applicationUserUsername)));
        employees = calculateEmployeeHourBalance(employees);
        final List<EmployeeDto> convertedEmployees = new ArrayList<>();
        for (EmployeeEntity employeeEntity : employees) {
            EmployeeDto employeeDto = employeeMapper.toDto(employeeEntity);
            employeeDto.setBalance(String.valueOf(employeeEntity.getStatistic().getBalance()));
            employeeDto.setOvertime(toOvertimeDtoSet(employeeEntity));
            convertedEmployees.add(employeeDto);
        }
        return convertedEmployees;
    }

    private Set<OvertimeDto> toOvertimeDtoSet(EmployeeEntity employeeEntity) {
        Set<OvertimeDto> overtimeDtoSet = new HashSet<>();
        if (employeeEntity.getStatistic() != null && employeeEntity.getStatistic().getOvertime() != null) {
            for (OvertimeEntity overtimeEntity : employeeEntity.getStatistic().getOvertime()) {
                overtimeDtoSet.add(overtimeMapper.toDto(overtimeEntity));
            }
        }
        return overtimeDtoSet;
    }

    private List<EmployeeEntity> calculateEmployeeHourBalance(List<EmployeeEntity> employees) {
        List<EmployeeEntity> employeesWithBalance = new ArrayList<>();
        for (EmployeeEntity e : employees) {
            employeesWithBalance.add(calculateEmployeeHourBalance(e));
        }
        return employeesWithBalance;
    }

    private EmployeeEntity calculateEmployeeHourBalance(EmployeeEntity employee) {
        CustomHourStatisticEntity customHourStatistic = employee.initializeStats();
        Set<OvertimeEntity> overtimeSet = customHourStatistic.getOvertime();
        if (overtimeSet != null) {
            List<Duration> durationList = customHourStatistic.getOvertime()
                    .stream()
                    .filter((a -> a.getPickUpDate() == null))
                    .map(OvertimeEntity::getAmount)
                    .collect(Collectors.toList());

            Duration duration = Duration.ZERO;
            for (Duration d : durationList) {
                duration = duration.plus(d);
            }
            customHourStatistic.setBalance(duration);
            EmployeeEntity employeeWithBalance = new EmployeeEntity(employee);
            employeeWithBalance.setStatistic(customHourStatistic);
            return employeeWithBalance;
        }
        return employee;
    }

    @Override
    public void delete(String id) {
        Long parsedId = Long.parseLong(id);
        EmployeeEntity employee = findById(parsedId);

        checkIfIsAuthorizedToAccessEmployee(employee);

        employeeRepository.delete(employee);
    }

    @Override
    public void addOvertime(Long id, OvertimeDto overtime) {
        OvertimeEntity entity = new OvertimeEntity();
        if (!StringUtils.isEmpty(overtime.getAmount())) {
            try {
                Duration amount = Duration.parse(overtime.getAmount());
                entity.setAmount(amount);
            } catch (DateTimeParseException e) {
                log.warn("Invalid amount format passed");
            }
        }
        if (!StringUtils.isEmpty(overtime.getOvertimeDate()) && !overtime.getOvertimeDate().equals("null")) {
            try {
                LocalDate date = LocalDate.parse(overtime.getOvertimeDate());
                entity.setOvertimeDate(date);
            } catch (DateTimeParseException e) {
                log.warn("Invalid overtime date format passed");
            }
        }
        if (!StringUtils.isEmpty(overtime.getPickUpDate()) && !overtime.getPickUpDate().equals("null")) {
            try {
                LocalDate date = LocalDate.parse(overtime.getPickUpDate());
                entity.setPickUpDate(date);
            } catch (DateTimeParseException e) {
                log.warn("Invalid pickup date format passed");
            }
        }
        entity.setRemarks(overtime.getRemarks());
        entity.setId(overtime.getId());
        EmployeeEntity employee = findById(id);
        checkIfIsAuthorizedToAccessEmployee(employee);

        CustomHourStatisticEntity statistic = employee.initializeStats();
        Set<OvertimeEntity> overtimeSet = statistic.initializeOvertime();

        statistic.setEmployee(employee);

        entity.setCustomHourStatistic(statistic);
        overtimeSet.remove(entity);
        overtimeSet.add(entity);

        statistic.setOvertime(overtimeSet);

        employee.setStatistic(statistic);

        employeeRepository.save(employee);
    }

    @Override
    public void deleteOvertime(Long employeeId, Long overtimeId) {
        EmployeeEntity employee = findById(employeeId);
        checkIfIsAuthorizedToAccessEmployee(employee);
        overtimeRepository.delete(overtimeRepository.findById(overtimeId)
                .orElseThrow(OvertimeIdNotFoundException::new));

    }

    private EmployeeEntity findById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeIdNotFoundException
                ("Employee with id " + id + " was not found"));
    }

    private void checkIfIsAuthorizedToAccessEmployee(EmployeeEntity employee) {
        String applicationUserUsername = authorizationHolder.loadCurrentUserUsername();
        if (!employee.getApplicationUser().getUsername().equals(applicationUserUsername)) {
            throw new UnathorizedDeleteAttemptException
                    ("Provided employee id doesn't belong to user with username " + applicationUserUsername);
        }
    }


}
