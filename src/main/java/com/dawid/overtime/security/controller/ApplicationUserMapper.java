package com.dawid.overtime.security.controller;

import com.dawid.overtime.dto.ApplicationUserDto;
import com.dawid.overtime.entity.ApplicationUserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicationUserMapper {
    ApplicationUserEntity toEntity(ApplicationUserDto entity);

}
