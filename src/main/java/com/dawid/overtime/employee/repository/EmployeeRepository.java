package com.dawid.overtime.employee.repository;

import com.dawid.overtime.entity.ApplicationUserEntity;
import com.dawid.overtime.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    List<EmployeeEntity> findAllByApplicationUser(ApplicationUserEntity applicationUser);
}
