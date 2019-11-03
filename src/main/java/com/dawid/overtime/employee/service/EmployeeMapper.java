package com.dawid.overtime.employee.service;

import com.dawid.overtime.dto.EmployeeDto;
import com.dawid.overtime.entity.EmployeeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeDto toDto(EmployeeEntity entity);
}
