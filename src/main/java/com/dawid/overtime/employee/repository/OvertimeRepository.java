package com.dawid.overtime.employee.repository;

import com.dawid.overtime.entity.OvertimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OvertimeRepository extends JpaRepository<OvertimeEntity, Long> {
}
