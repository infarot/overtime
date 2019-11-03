package com.dawid.overtime.employee.service;

import com.dawid.overtime.dto.OvertimeDto;
import com.dawid.overtime.entity.OvertimeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OvertimeMapper {
    OvertimeDto toDto(OvertimeEntity entity);
}
