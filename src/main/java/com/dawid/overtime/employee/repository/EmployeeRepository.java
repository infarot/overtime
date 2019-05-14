package com.dawid.overtime.employee.repository;

import com.dawid.overtime.entity.Employee;
import com.dawid.overtime.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByApplicationUser(ApplicationUser applicationUser);
}
