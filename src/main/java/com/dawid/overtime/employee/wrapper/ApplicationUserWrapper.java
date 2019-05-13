package com.dawid.overtime.employee.wrapper;

import com.dawid.overtime.security.entity.ApplicationUser;

import java.util.Optional;

public interface ApplicationUserWrapper {
    Optional<ApplicationUser> findByUsername(String username);
}
