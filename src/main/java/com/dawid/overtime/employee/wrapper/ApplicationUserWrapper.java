package com.dawid.overtime.employee.wrapper;

import com.dawid.overtime.entity.ApplicationUserEntity;

import java.util.Optional;

public interface ApplicationUserWrapper {
    Optional<ApplicationUserEntity> findByUsername(String username);
}
